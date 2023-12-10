package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioFiles.Song;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Extras.RES_COUNT_MAX;

/**
 * This class implements the command strategy for getting the top 5 songs.
 */
public final class GetTop5Songs implements Command {
    @Override
    public Output action(final Input input) {
        ArrayList<Song> songs = new ArrayList<>(Database.getInstance().getSongs());
        songs.sort(Comparator
                .<Song, Integer>comparing(song -> song.getUsersWhoLiked().size())
                .reversed()
                .thenComparing(songs::indexOf));

        ArrayList<Object> result = new ArrayList<>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, songs.size()); i++) {
            result.add(songs.get(i).getName());
        }

        return new Output(input, result, null);
    }
}
