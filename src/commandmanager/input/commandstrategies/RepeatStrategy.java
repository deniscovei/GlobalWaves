package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import utils.Constants;
import data.Database;
import data.entities.user.User;

import java.util.Objects;

/**
 * This class represents the strategy for setting the repeat status of a source.
 */
public final class RepeatStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).hasLoadedAFile()) {
            message = "Please load a source before setting the repeat status.";
        } else {
            user.getPlayer().repeat(input.getTimestamp());
            Constants.FileType fileType = user.getLoadedFile().getFileType();
            int repeatState = user.getPlayer().getRepeatState();
            String repeatStateLowerCase = Constants
                    .repeatStateToString(repeatState, fileType).toLowerCase();
            message = "Repeat mode changed to " + repeatStateLowerCase + ".";
        }

        return new Output(input, message);
    }
}
