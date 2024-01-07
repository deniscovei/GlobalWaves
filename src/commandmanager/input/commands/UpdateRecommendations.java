package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils;

public class UpdateRecommendations implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != AppUtils.UserType.LISTENER) {
            message = input.getUsername() + " is not a normal user.";
        } else {
            Listener listener = (Listener) user;

            if (listener.updateRecommendations(
                AppUtils.stringToRecommendationType(input.getRecommendationType()),
                input.getTimestamp())) {
                message = "The recommendations for user " + input.getUsername()
                    + " have been updated successfully.";

            } else {
                message = "No new recommendations were found";
            }
        }

        return new Output(input, message);
    }
}
