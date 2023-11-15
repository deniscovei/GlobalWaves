package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioCollections.AudioCollection;
import data.entities.audio.audioCollections.Playlist;
import data.entities.user.User;

public final class LoadStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        String message;

        if (user.getSelection() == null) {
            message = "Please select a source before attempting to load.";
        } else if (!(user.getSelection() instanceof File)) {
            message = "You can't load an empty audio collection!";
        } else {
            user.loadAudioFile();
            user.getSelection().play(inputCommand.getTimestamp());
            message = "Playback loaded successfully.";
        }

        return new OutputCommand(inputCommand, message);
    }
}
