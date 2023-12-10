package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;
import utils.Extras;

public final class SwitchConnectionStatus implements Command {
    @Override
    public Output action(Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != Extras.UserType.LISTENER) {
            message = user.getUsername() + " is not a normal user.";
        } else {
            user.switchConnectionStatus(input.getTimestamp());
            message = user.getUsername() + " has changed status successfully.";
        }

        return new Output(input, message);
    }
}
