package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.user.User;

public final class PlayPauseStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        String message;

        if (!user.hasLoadedAudioFile()) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else if (user.getSelection().isPlaying()) {
            user.getSelection().pause(inputCommand.getTimestamp());
            message = "Playback paused successfully.";
        } else {
            user.getSelection().play(inputCommand.getTimestamp());
            message = "Playback resumed successfully.";
        }

        return new OutputCommand(inputCommand, message);
    }
}
