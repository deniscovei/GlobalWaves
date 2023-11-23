package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.user.User;
import utils.Constants;

import java.util.Objects;

/**
 * This class represents the strategy for setting the shuffle status of a source.
 */
public final class ShuffleStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before using the shuffle function.";
        } else if (!user.getLoadedFile().getFileType().equals(Constants.FileType.PLAYLIST)) {
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
