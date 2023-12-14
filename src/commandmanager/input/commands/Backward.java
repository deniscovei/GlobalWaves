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
public final class Backward implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please select a source before rewinding.";
        } else if (!user.getLoadedFile().getFileType().equals(AppUtils.FileType.PODCAST)) {
            message = "The loaded source is not a podcast.";
        } else {
            user.getPlayer().backward(input.getTimestamp());
            message = "Rewound successfully.";
        }

        return new Output(input, message);
    }
}
