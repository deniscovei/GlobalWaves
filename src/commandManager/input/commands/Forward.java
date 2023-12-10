package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;
import utils.Extras;

import java.util.Objects;

/**
 * This class represents the strategy for rewinding a podcast.
 */
public final class Forward implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;

        if (!Objects.requireNonNull(user).hasLoadedAFile()
            || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before attempting to forward.";
        } else if (!user.getLoadedFile().getFileType().equals(Extras.FileType.PODCAST)) {
            message = "The loaded source is not a podcast.";
        } else {
            user.getPlayer().forward(input.getTimestamp());
            message = "Skipped forward successfully.";
        }

        return new Output(input, message);
    }
}
