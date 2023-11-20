package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import utils.Constants;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Constants.RES_COUNT_MAX;

public class GetTop5PlaylistsStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getVisibility().equals(Constants.PUBLIC))
                playlists.add(playlist);
        }
        playlists.sort(Comparator.comparingInt(Playlist::getFollowers).reversed().thenComparing(Playlist::getCreatedAt));

        ArrayList <Object> result = new ArrayList <>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, playlists.size()); i++) {
            result.add(playlists.get(i).getName());
        }

        return new Output(input, result, null);
    }
}
