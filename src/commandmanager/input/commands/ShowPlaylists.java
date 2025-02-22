package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioCollections.Playlist;

import java.util.List;

/**
 * This class implements the command strategy for showing the playlists.
 */
public final class ShowPlaylists implements Command {
    @Override
    public Output action(final Input input) {
        List<Playlist> result = Database.getInstance().getOwnedPlaylists(input.getUsername());

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .result(result)
            .build();
    }
}
