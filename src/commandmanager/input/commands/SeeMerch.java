package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

public final class SeeMerch implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (user == null) {
            return new Output.Builder()
                .command(input.getCommand())
                .timestamp(input.getTimestamp())
                .user(input.getUsername())
                .message("The username " + input.getUsername() + " doesn't exist.")
                .build();
        } else {
            return new Output.Builder()
                .command(input.getCommand())
                .timestamp(input.getTimestamp())
                .user(input.getUsername())
                .result(user.getMerches())
                .build();
        }
    }
}
