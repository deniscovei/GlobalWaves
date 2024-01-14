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
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            Playlist playlist = Database.getInstance().findPlaylist(input.getPlaylistId(),
                input.getUsername());

            if (playlist == null) {
                message = "The specified playlist ID is too high.";
            } else {
                playlist.switchVisibility();
                message = "Visibility status updated successfully to "
                    + playlist.getVisibility() + ".";
            }
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
