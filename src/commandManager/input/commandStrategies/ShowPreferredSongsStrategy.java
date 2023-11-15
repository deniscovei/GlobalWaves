package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;

import java.util.ArrayList;

public final class ShowPreferredSongsStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        ArrayList <Object> result = new ArrayList <>();

        for (Song song : user.getPrefferedSongs()) {
            result.add(song.getName());
        }

        return new OutputCommand(inputCommand, result, null);
    }
}
