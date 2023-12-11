package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.Extras.UserType;

public final class SwitchConnectionStatus implements Command {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.LISTENER) {
            message = user.getUsername() + " is not a normal user.";
        } else {
            Listener listener = (Listener) user;
            listener.switchConnectionStatus(input.getTimestamp());
            message = listener.getUsername() + " has changed status successfully.";
        }

        return new Output(input, message);
    }
}
