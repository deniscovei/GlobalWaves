package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.user.User;
import utils.Constants;

import java.util.Objects;

/**
 * This class represents the strategy for rewinding a podcast.
 */
public final class ForwardStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before attempting to forward.";
        } else if (!user.getLoadedFile().getFileType().equals(Constants.FileType.PODCAST)) {
            message = "The loaded source is not a podcast.";
        } else {
            user.getPlayer().forward(input.getTimestamp());
            message = "Skipped forward successfully.";
        }

        return new Output(input, message);
    }
}
