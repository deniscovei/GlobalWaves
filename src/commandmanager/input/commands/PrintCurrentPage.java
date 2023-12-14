package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils.UserType;

import java.util.Objects;

public final class PrintCurrentPage implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        if (Objects.requireNonNull(user).getUserType().equals(UserType.LISTENER)
            && !((Listener) user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        return new Output(input, user.getCurrentPage().getFormat());
    }
}
