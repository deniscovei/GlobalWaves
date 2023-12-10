package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.content.Merch;
import data.entities.users.Artist;
import data.entities.users.User;
import utils.Extras;

public final class AddMerch implements Command {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != Extras.UserType.ARTIST) {
            message = input.getUsername() + " is not an artist.";
        } else if (((Artist) user).findMerch(input.getName()) != null) {
            message = input.getUsername() + " has merchandise with the same name.";
        } else if (input.getPrice() < 0) {
            message = "Price for merchandise can not be negative.";
        } else {
            ((Artist) user).addMerch(new Merch(input.getName(), input.getDescription(), input.getPrice()));
            message = input.getUsername() + " has added new merchandise successfully.";
        }

        return new Output(input, message);
    }
}