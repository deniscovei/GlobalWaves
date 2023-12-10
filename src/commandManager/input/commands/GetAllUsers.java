package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.User;
import utils.Extras.UserType;

import java.util.ArrayList;

public final class GetAllUsers implements Command {
    @Override
    public Output action(Input input) {
        ArrayList<String> listeners = new ArrayList<>();
        ArrayList<String> artists = new ArrayList<>();
        ArrayList<String> hosts = new ArrayList<>();

        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == UserType.LISTENER) {
                listeners.add(user.getUsername());
            }
            if (user.getUserType() == UserType.ARTIST) {
                artists.add(user.getUsername());
            }
            if (user.getUserType() == UserType.HOST) {
                hosts.add(user.getUsername());
            }
        }

        ArrayList<String> result = new ArrayList<>();
        result.addAll(listeners);
        result.addAll(artists);
        result.addAll(hosts);

        return new Output(input, result, null);
    }
}
