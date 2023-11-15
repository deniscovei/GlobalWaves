package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.user.User;

public final class CreatePlaylistStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());

        if (Database.getInstance().findPlaylist(inputCommand.getPlaylistName()) != null) {
            return new OutputCommand(inputCommand, "A playlist with the same name already exists.");
        } else {
            Playlist playlist = new Playlist(inputCommand.getPlaylistName(), user.getUsername());
            Database.getInstance().addPlaylist(playlist);
            return new OutputCommand(inputCommand, "Playlist created successfully.");
        }
    }
}
