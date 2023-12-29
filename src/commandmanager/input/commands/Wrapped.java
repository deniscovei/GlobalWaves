package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Artist;
import data.entities.users.Host;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils;

import java.util.Objects;

public class Wrapped implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        switch (Objects.requireNonNull(user).getUserType()) {
            case ARTIST:
                if (((Artist.ArtistTops) user.getTops()).getListeners() == 0) {
                    return new Output(input, "No data to show for user "
                            + input.getUsername() + ".");
                }
                break;
            case HOST:
                if (((Host.HostTops) user.getTops()).getListeners() == 0) {
                    return new Output(input, "No data to show for user "
                            + input.getUsername() + ".");
                }
                break;
            case LISTENER:
                if (((Listener.ListenerTops) user.getTops()).getTopSongs().isEmpty()
                        && ((Listener.ListenerTops) user.getTops()).getTopEpisodes().isEmpty()) {
                    return new Output(input, "No data to show for user "
                            + input.getUsername() + ".");
                }
                break;
        }

        User.Tops result = Objects.requireNonNull(user).getTops().clone();

        return new Output(input, result, null);
    }
}
