package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.Extras.UserType;

import java.util.Objects;

public final class PrintCurrentPage implements Command {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        if (Objects.requireNonNull(user).getUserType().equals(UserType.LISTENER) && !((Listener) user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        return new Output(input, user.getCurrentPage().getFormat());
    }
}
