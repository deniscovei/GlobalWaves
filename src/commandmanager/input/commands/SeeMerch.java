package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

public class SeeMerch implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (user == null) {
            return new Output(input, "The username " + input.getUsername() + " doesn't exist.");
        } else {
            return new Output(input, user.getMerches(), null);
        }
    }
}
