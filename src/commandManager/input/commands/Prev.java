package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.Listener;

import java.util.Objects;

/**
 * This class represents the strategy for returning to the previous track.
 */
public final class Prev implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;

        boolean prevSucceded = Objects.requireNonNull(user).hasLoadedAFile()
                && !user.getPlayer().hasFinished(input.getTimestamp())
                && user.getPlayer().prev(input.getTimestamp());

        if (prevSucceded) {
            message = "Returned to previous track successfully. The current track is "
                    + user.getPlayer().getCurrentPlayingFile(input.getTimestamp()).getName() + ".";
        } else {
            message = "Please load a source before returning to the previous track.";
        }

        return new Output(input, message);
    }
}
