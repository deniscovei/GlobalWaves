package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;
import utils.AppUtils.FileType;

import java.util.Objects;

/**
 * This class represents the strategy for setting the shuffle status of a source.
 */
public final class Shuffle implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before using the shuffle function.";
        } else if (user.getLoadedFile().getFileType() != FileType.PLAYLIST
                && user.getLoadedFile().getFileType() != FileType.ALBUM) {
            message = "The loaded source is not a playlist or an album.";
        } else {
            if (user.getPlayer().isShuffleActivated()) {
                user.getPlayer().unshuffle(input.getTimestamp());
                message = "Shuffle function deactivated successfully.";
            } else {
                user.getPlayer().shuffle(input.getSeed(), input.getTimestamp());
                message = "Shuffle function activated successfully.";
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
