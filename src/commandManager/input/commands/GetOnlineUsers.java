package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.Extras.UserType;

import java.util.ArrayList;

public final class GetOnlineUsers implements Command {
    @Override
    public Output action(Input input) {
        ArrayList<User> users = Database.getInstance().getUsers();
        ArrayList<String> result = new ArrayList<>();

        for (User user : users) {
            if (user.getUserType().equals(UserType.LISTENER) && ((Listener) user).isOnline()) {
                result.add(user.getUsername());
            }
        }

        return new Output(input, result, null);
    }
}
