package commands.derived.input.commandTypes;

import commands.derived.input.inputCommand.InputCommand;
import commands.derived.output.OutputCommand;
import data.database.Database;
import data.entities.audio.derived.Playlist;
import data.entities.user.User;

public final class Load {
    public static OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        String message;

        if (user.getSelection() == null) {
            message = "Please select a source before attempting to load.";
        } else if (user.getSelection() instanceof Playlist) {
            message = "You can't load an empty audio collection!";
        } else {
            user.loadAudioFile();
            message = "Playback loaded successfully.";
        }

        return new OutputCommand(inputCommand, message);
    }
}
