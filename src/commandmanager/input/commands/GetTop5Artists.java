package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.contentCreator.Artist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.AppUtils.RES_COUNT_MAX;

public final class GetTop5Artists implements Command {
    @Override
    public Output action(final Input input) {
        List<Artist> artists = new ArrayList<>(Database.getInstance().getArtists());
        artists.sort(Comparator.comparingInt(Artist::getNumberOfLikes).reversed());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, artists.size()); i++) {
            result.add(artists.get(i).getUsername());
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .result(result)
            .build();
    }
}
