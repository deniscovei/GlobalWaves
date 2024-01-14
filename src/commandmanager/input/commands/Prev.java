package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

import java.util.Objects;

/**
 * This class represents the strategy for returning to the previous track.
 */
public final class Prev implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            boolean prevSucceded = Objects.requireNonNull(user).hasLoadedAFile()
                && !user.getPlayer().hasFinished(input.getTimestamp())
                && user.getPlayer().prev(input.getTimestamp());

            if (prevSucceded) {
                message = "Returned to previous track successfully. The current track is "
                    + user.getPlayer().getCurrentPlayingFile(input.getTimestamp()).getName() + ".";
            } else {
                message = "Please load a source before returning to the previous track.";
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
