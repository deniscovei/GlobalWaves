package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.content.Merchandise;
import data.entities.users.contentCreator.Artist;
import data.entities.users.User;
import utils.AppUtils;

public final class AddMerch implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != AppUtils.UserType.ARTIST) {
            message = input.getUsername() + " is not an artist.";
        } else if (((Artist) user).findMerch(input.getName()) != null) {
            message = input.getUsername() + " has merchandise with the same name.";
        } else if (input.getPrice() < 0) {
            message = "Price for merchandise can not be negative.";
        } else {
            ((Artist) user).addMerchandise(new Merchandise(input.getName(), input.getDescription(),
                                                     input.getPrice()));
            message = input.getUsername() + " has added new merchandise successfully.";
        }

        return new Output(input, message);
    }
}
