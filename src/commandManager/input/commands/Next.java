package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.Listener;

import java.util.Objects;

/**
 * This class represents the strategy for skipping to the next track.
 */
public final class Next implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;

        boolean nextSucceeded = Objects.requireNonNull(user).hasLoadedAFile()
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
