package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audiofiles.Song;
import data.entities.user.User;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class implements the command strategy for showing the preferred songs.
 */
public final class ShowPreferredSongsStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        ArrayList<Object> result = new ArrayList<>();

        for (Song song : Objects.requireNonNull(user).getPreferredSongs()) {
            result.add(song.getName());
        }

        return new Output(input, result, null);
    }
}
