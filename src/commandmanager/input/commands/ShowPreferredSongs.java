package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioFiles.Song;
import data.entities.users.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the command strategy for showing the preferred songs.
 */
public final class ShowPreferredSongs implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        List<String> result = new ArrayList<>();

        for (Song song : Objects.requireNonNull(user).getLikedSongs()) {
            result.add(song.getName());
        }

        return new Output(input, result, null);
    }
}
