package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.entities.users.Listener;
import utils.AppUtils;
import data.Database;

import java.util.Objects;

/**
 * This class represents the strategy for setting the repeat status of a source.
 */
public final class Repeat implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before setting the repeat status.";
        } else {
            user.getPlayer().repeat(input.getTimestamp());
            AppUtils.FileType fileType = user.getLoadedFile().getFileType();
            int repeatState = user.getPlayer().getRepeatState();
            String repeatStateLowerCase = AppUtils
                    .repeatStateToString(repeatState, fileType).toLowerCase();
            message = "Repeat mode changed to " + repeatStateLowerCase + ".";
        }

        return new Output(input, message);
    }
}
