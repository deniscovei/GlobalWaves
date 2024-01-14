package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

import java.util.Objects;

/**
 * This class represents the strategy for skipping to the next track.
 */
public final class Next implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
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
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
