package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audiocollections.Playlist;
import data.entities.user.User;

import java.util.Objects;

/**
 * This class represents the strategy for creating a playlist.
 */
public final class CreatePlaylistStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        if (Database.getInstance().findPlaylist(input.getPlaylistName()) != null) {
            return new Output(input, "A playlist with the same name already exists.");
        } else {
            Playlist playlist = new Playlist(input.getPlaylistName(),
                                             Objects.requireNonNull(user).getUsername(),
                                             input.getTimestamp());
            Database.getInstance().addPlaylist(playlist);
            return new Output(input, "Playlist created successfully.");
        }
    }
}
