package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.user.User;

public final class CreatePlaylistStrategy implements CommandStrategy {
    public Output action(Input inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());

        if (Database.getInstance().findPlaylist(inputCommand.getPlaylistName()) != null) {
            return new Output(inputCommand, "A playlist with the same name already exists.");
        } else {
            Playlist playlist = new Playlist(inputCommand.getPlaylistName(), user.getUsername());
            Database.getInstance().addPlaylist(playlist);
            return new Output(inputCommand, "Playlist created successfully.");
        }
    }
}
