package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.entities.users.Listener;
import utils.Extras;
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
            Extras.FileType fileType = user.getLoadedFile().getFileType();
            int repeatState = user.getPlayer().getRepeatState();
            String repeatStateLowerCase = Extras
                    .repeatStateToString(repeatState, fileType).toLowerCase();
            message = "Repeat mode changed to " + repeatStateLowerCase + ".";
        }

        return new Output(input, message);
    }
}
