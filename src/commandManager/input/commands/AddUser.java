package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import utils.Extras;

public final class AddUser implements Command {
    @Override
    public Output action(Input input) {
        if (Database.getInstance().findUser(input.getUsername()) != null) {
            return new Output(input, "The username " + input.getUsername() + " is already taken.");
        } else {
            Database.getInstance().addUser(input.getUsername(), input.getAge(), input.getCity(),
                    Extras.stringToUserType(input.getType()));
            return new Output(input, "The username " + input.getUsername() + " has been added successfully.");
        }
    }
}
