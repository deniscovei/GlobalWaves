package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;

import java.util.ArrayList;

/**
 * This class implements the command strategy for showing the playlists.
 */
public final class ShowPlaylists implements Command {
    @Override
    public Output action(final Input input) {
        ArrayList<Playlist> result = Database.getInstance().getFollowedPlaylists(input.getUsername());
        return new Output(input, result, null);
    }
}
