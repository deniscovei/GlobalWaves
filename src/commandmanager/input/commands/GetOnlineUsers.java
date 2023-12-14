package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils.UserType;

import java.util.ArrayList;
import java.util.List;

public final class GetOnlineUsers implements Command {
    @Override
    public Output action(final Input input) {
        List<User> users = Database.getInstance().getUsers();
        List<String> result = new ArrayList<>();

        for (User user : users) {
            if (user.getUserType().equals(UserType.LISTENER) && ((Listener) user).isOnline()) {
                result.add(user.getUsername());
            }
        }

        return new Output(input, result, null);
    }
}
