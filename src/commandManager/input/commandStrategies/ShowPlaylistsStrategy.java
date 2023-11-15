package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioCollections.Playlist;

import java.util.ArrayList;

public final class ShowPlaylistsStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        ArrayList <Object> result = new ArrayList <>();
        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getOwner().equals(inputCommand.getUsername())) {
                result.add(playlist);
            }
        }
        return new OutputCommand(inputCommand, result, null);
    }
}
