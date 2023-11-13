package commands.derived.input.commandTypes;

import commands.derived.input.inputCommand.InputCommand;
import commands.derived.output.OutputCommand;
import data.database.Database;
import data.entities.audio.base.AudioFile;
import data.entities.user.User;

import java.util.ArrayList;

public final class PlayPause {
    public static OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        String message;

        if (user.hasLoadedAudioFile()) {
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
