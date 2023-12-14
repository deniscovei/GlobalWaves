package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.users.Listener;

import java.util.Objects;

/**
 * This class represents the strategy for creating a playlist.
 */
public final class CreatePlaylist implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        if (Database.getInstance().findPlaylist(input.getPlaylistName()) != null) {
            return new Output(input, "A playlist with the same name already exists.");
        }

        Playlist playlist = new Playlist(input.getPlaylistName(),
                Objects.requireNonNull(user).getUsername(),
                input.getTimestamp());
        Database.getInstance().addPlaylist(playlist);
        return new Output(input, "Playlist created successfully.");
    }
}
