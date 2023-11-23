package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.audio.audiocollections.Playlist;

/**
 * This class implements the command strategy for switching the visibility of a playlist.
 */
public final class SwitchVisibilityStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        Playlist playlist = Database.getInstance().findPlaylist(input.getPlaylistId(),
                input.getUsername());
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
