package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import utils.AppUtils;

public final class AddUser implements Command {
    @Override
    public Output action(final Input input) {
        if (Database.getInstance().findUser(input.getUsername()) != null) {
            return new Output(input, "The username " + input.getUsername()
                + " is already taken.");
        } else {
            Database.getInstance().addUser(input.getUsername(), input.getAge(), input.getCity(),
                    AppUtils.stringToUserType(input.getType()));
            return new Output(input, "The username " + input.getUsername()
                + " has been added successfully.");
        }
    }
}
