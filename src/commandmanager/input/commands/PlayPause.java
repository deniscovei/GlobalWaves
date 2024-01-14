package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
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
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else if (!user.getPlayer().isPaused()) {
            user.getPlayer().pause(input.getTimestamp());
            message = "Playback paused successfully.";
        } else {
            user.getPlayer().play(input.getTimestamp());
            message = "Playback resumed successfully.";
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
