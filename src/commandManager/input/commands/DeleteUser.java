package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.User;

public final class DeleteUser implements Command {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.interactingWithOthers(input.getTimestamp())) {
            message = input.getUsername() + " can't be deleted.";
        } else {
            Database.getInstance().removeUser(user);
            message = input.getUsername() + " was successfully deleted.";
        }

        return new Output(input, message);
    }
}
