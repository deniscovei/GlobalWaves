package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.user.User;

/**
 * This class represents the strategy for playing or pausing a track.
 */
public final class PlayPauseStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else if (!user.getPlayer().isPaused()) {
            user.getPlayer().pause(input.getTimestamp());
            message = "Playback paused successfully.";
        } else {
            user.getPlayer().play(input.getTimestamp());
            message = "Playback resumed successfully.";
        }

        return new Output(input, message);
    }
}
