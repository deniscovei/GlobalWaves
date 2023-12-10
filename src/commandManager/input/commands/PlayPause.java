package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;

import java.util.Objects;

/**
 * This class represents the strategy for playing or pausing a track.
 */
public final class PlayPause implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;

        if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
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
