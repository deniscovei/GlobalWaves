package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;

import java.util.ArrayList;

public final class ShowPreferredSongsStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        ArrayList <Object> result = new ArrayList <>();

        for (Song song : user.getPrefferedSongs()) {
            result.add(song.getName());
        }

        return new Output(input, result, null);
    }
}
