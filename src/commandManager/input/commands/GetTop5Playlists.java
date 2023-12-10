package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import utils.Extras;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Extras.RES_COUNT_MAX;

/**
 * This class implements the command strategy for getting the top 5 playlists.
 */
public final class GetTop5Playlists implements Command {
    @Override
    public Output action(final Input input) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getVisibility().equals(Extras.PUBLIC)) {
                playlists.add(playlist);
            }
        }
        playlists.sort(Comparator
                .comparingInt(Playlist::getFollowers).reversed()
                .thenComparing(Playlist::getCreatedAt));

        ArrayList<Object> result = new ArrayList<>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, playlists.size()); i++) {
            result.add(playlists.get(i).getName());
        }

        return new Output(input, result, null);
    }
}
