package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioFiles.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.AppUtils.RES_COUNT_MAX;

/**
 * This class implements the command strategy for getting the top 5 songs.
 */
public final class GetTop5Songs implements Command {
    @Override
    public Output action(final Input input) {
        List<Song> songs = new ArrayList<>(Database.getInstance().getSongs());

        songs.sort(Comparator
                .<Song, Integer>comparing(song -> song.getUsersWhoLiked().size())
                .reversed());

        List<String> result = new ArrayList<>();

        for (int i = 0; i < Math.min(RES_COUNT_MAX, songs.size()); i++) {
            result.add(songs.get(i).getName());
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .result(result)
            .build();
    }
}
