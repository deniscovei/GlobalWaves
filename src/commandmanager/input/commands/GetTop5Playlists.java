package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import utils.AppUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.AppUtils.RES_COUNT_MAX;

/**
 * This class implements the command strategy for getting the top 5 playlists.
 */
public final class GetTop5Playlists implements Command {
    @Override
    public Output action(final Input input) {
        List<Playlist> playlists = new ArrayList<>();
        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getVisibility().equals(AppUtils.PUBLIC)) {
                playlists.add(playlist);
            }
        }
        playlists.sort(Comparator
                .comparingInt(Playlist::getFollowers).reversed()
                .thenComparing(Playlist::getCreatedAt));

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, playlists.size()); i++) {
            result.add(playlists.get(i).getName());
        }

        return new Output(input, result, null);
    }
}
