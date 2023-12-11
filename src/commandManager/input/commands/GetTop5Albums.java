package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Album;
import data.entities.audio.audioCollections.Playlist;
import utils.Extras;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Extras.RES_COUNT_MAX;

public final class GetTop5Albums implements Command {
    @Override
    public Output action(final Input input) {
        ArrayList<Album> albums = new ArrayList<>(Database.getInstance().getAlbums());

        albums.sort(Comparator
                .comparingInt(Album::getNumberOfLikes).reversed()
                .thenComparing(Album::getName));

        ArrayList<Object> result = new ArrayList<>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, albums.size()); i++) {
            result.add(albums.get(i).getName());
        }

        return new Output(input, result, null);
    }
}
