package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.user.User;

/**
 * This class represents the strategy for skipping to the next track.
 */
public final class NextStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        boolean nextSucceeded = user.hasLoadedAFile()
                && !user.getPlayer().hasFinished(input.getTimestamp())
                && user.getPlayer().next(input.getTimestamp());

        if (nextSucceeded) {
            message = "Skipped to next track successfully. The current track is "
                    + user.getPlayer().getCurrentPlayingFile(input.getTimestamp()).getName() + ".";
        } else {
            user.unloadAudioFile(input.getTimestamp());
            message = "Please load a source before skipping to the next track.";
        }

        return new Output(input, message);
    }
}
