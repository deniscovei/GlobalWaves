package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;
import utils.AppUtils;

import java.util.Objects;

/**
 * This class represents the strategy for rewinding a podcast.
 */
public final class Forward implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (!Objects.requireNonNull(user).hasLoadedAFile()
            || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before attempting to forward.";
        } else if (!user.getLoadedFile().getFileType().equals(AppUtils.FileType.PODCAST)) {
            message = "The loaded source is not a podcast.";
        } else {
            user.getPlayer().forward(input.getTimestamp());
            message = "Skipped forward successfully.";
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
