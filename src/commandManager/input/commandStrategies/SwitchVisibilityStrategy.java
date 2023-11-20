package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import utils.Constants;

public class SwitchVisibilityStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        Playlist playlist = Database.getInstance().findPlaylist(input.getPlaylistId(), input.getUsername());
        String message;

        if (playlist == null) {
            message = "The specified playlist ID is too high.";
        } else {
            playlist.switchVisibility();
            message = "Visibility status updated successfully to " + playlist.getVisibility() + ".";
        }

        return new Output(input, message);
    }
}
