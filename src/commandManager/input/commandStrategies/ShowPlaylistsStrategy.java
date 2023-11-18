package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;

import java.util.ArrayList;

public final class ShowPlaylistsStrategy implements CommandStrategy {
    public Output action(Input inputCommand) {
        ArrayList <Object> result = new ArrayList <>();
        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getOwner().equals(inputCommand.getUsername())) {
                result.add(playlist);
            }
        }
        return new Output(inputCommand, result, null);
    }
}
