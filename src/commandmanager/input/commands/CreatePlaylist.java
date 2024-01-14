package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioCollections.Playlist;
import data.entities.users.Listener;

import java.util.Objects;

/**
 * This class represents the strategy for creating a playlist.
 */
public final class CreatePlaylist implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (Database.getInstance().findPlaylist(input.getPlaylistName()) != null) {
            message = "A playlist with the same name already exists.";
        } else {
            message = "Playlist created successfully.";
        }

        Playlist playlist = new Playlist(input.getPlaylistName(),
                Objects.requireNonNull(user).getUsername(),
                input.getTimestamp());
        Database.getInstance().addPlaylist(playlist);

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
