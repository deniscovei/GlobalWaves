package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioFiles.Song;
import data.entities.user.Listener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class implements the command strategy for showing the preferred songs.
 */
public final class ShowPreferredSongs implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        ArrayList<Object> result = new ArrayList<>();

        for (Song song : Objects.requireNonNull(user).getLikedSongs()) {
            result.add(song.getName());
        }

        return new Output(input, result, null);
    }
}
