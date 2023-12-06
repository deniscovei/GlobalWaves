package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audiocollections.Playlist;

import java.util.ArrayList;

/**
 * This class implements the command strategy for showing the playlists.
 */
public final class ShowPlaylistsStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        ArrayList<Object> result = new ArrayList<>();
        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getOwner().equals(input.getUsername())) {
                result.add(playlist);
            }
        }
        return new Output(input, result, null);
    }
}
