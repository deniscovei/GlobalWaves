package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;

public class NextStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        boolean nextSucceeded = user.hasLoadedAFile() &&
                                !user.getPlayer().hasFinished(input.getTimestamp()) &&
                                user.getPlayer().next(input.getTimestamp());

        if (nextSucceeded) {
            message = "Skipped to next track successfully. The current track is " +
                      user.getPlayer().getCurrentPlayingFile(input.getTimestamp()).getName() + ".";
        } else {
            user.unloadAudioFile(input.getTimestamp());
            message = "Please load a source before skipping to the next track.";
        }

        return new Output(input, message);
    }
}
