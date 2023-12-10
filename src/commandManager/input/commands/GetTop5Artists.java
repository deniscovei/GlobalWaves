package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Artist;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Extras.RES_COUNT_MAX;

public final class GetTop5Artists implements Command {
    @Override
    public Output action(final Input input) {
        ArrayList<Artist> artists = new ArrayList<>(Database.getInstance().getArtists());
        artists.sort(Comparator.comparingInt(Artist::getNumberOfLikes).reversed());

        ArrayList<Object> result = new ArrayList<>();
        for (int i = 0; i < Math.min(RES_COUNT_MAX, artists.size()); i++) {
            result.add(artists.get(i).getUsername());
        }

        return new Output(input, result, null);
    }
}
