package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;
import utils.Extras;

import java.util.Objects;

/**
 * This class represents the strategy for setting the shuffle status of a source.
 */
public final class Shuffle implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;

        if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before using the shuffle function.";
        } else if (!user.getLoadedFile().getFileType().equals(Extras.FileType.PLAYLIST)) {
            message = "The loaded source is not a playlist.";
        } else {
            if (user.getPlayer().isShuffleActivated()) {
                user.getPlayer().unshuffle(input.getTimestamp());
                message = "Shuffle function deactivated successfully.";
            } else {
                user.getPlayer().shuffle(input.getSeed(), input.getTimestamp());
                message = "Shuffle function activated successfully.";
            }
        }

        return new Output(input, message);
    }
}
