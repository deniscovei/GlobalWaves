package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import utils.AppUtils;

public final class AddUser implements Command {
    @Override
    public Output action(final Input input) {
        String message;

        if (Database.getInstance().findUser(input.getUsername()) != null) {
            message = "The username " + input.getUsername() + " is already taken.";
        } else {
            Database.getInstance().addUser(input.getUsername(), input.getAge(), input.getCity(),
                    AppUtils.stringToUserType(input.getType()));
            message = "The username " + input.getUsername() + " has been added successfully.";
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
