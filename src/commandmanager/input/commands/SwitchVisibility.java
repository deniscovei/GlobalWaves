package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioCollections.Playlist;
import data.entities.users.Listener;

import java.util.Objects;

/**
 * This class implements the command strategy for switching the visibility of a playlist.
 */
public final class SwitchVisibility implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        Playlist playlist = Database.getInstance().findPlaylist(input.getPlaylistId(),
                input.getUsername());
        String message;

        if (playlist == null) {
            message = "The specified playlist ID is too high.";
        } else {
            playlist.switchVisibility();
            message = "Visibility status updated successfully to " + playlist.getVisibility() + ".";
        }

        return new Output(input, message);
    }
}
