package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.user.User;

public final class CreatePlaylistStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        if (Database.getInstance().findPlaylist(input.getPlaylistName()) != null) {
            return new Output(input, "A playlist with the same name already exists.");
        } else {
            Playlist playlist = new Playlist(input.getPlaylistName(), user.getUsername(), input.getTimestamp());
            Database.getInstance().addPlaylist(playlist);
            return new Output(input, "Playlist created successfully.");
        }
    }
}
