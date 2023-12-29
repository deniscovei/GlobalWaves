package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioCollections.Album;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.AppUtils.RES_COUNT_MAX;

public final class GetTop5Albums implements Command {
    @Override
    public Output action(final Input input) {
        List<Album> albums = new ArrayList<>(Database.getInstance().getAlbums());

        albums.sort(Comparator
                .comparingInt(Album::getNumberOfLikes).reversed()
                .thenComparing(Album::getName));

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, albums.size()); i++) {
            result.add(albums.get(i).getName());
        }

        return new Output(input, result, null);
    }
}
