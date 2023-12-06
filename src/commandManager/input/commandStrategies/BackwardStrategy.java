package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;
import utils.Constants;

/**
 * This class represents the strategy for rewinding a podcast.
 */
public final class BackwardStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please select a source before rewinding.";
        } else if (!user.getLoadedFile().getFileType().equals(Constants.FileType.PODCAST)) {
            message = "The loaded source is not a podcast.";
        } else {
            user.getPlayer().backward(input.getTimestamp());
            message = "Rewound successfully.";
        }

        return new Output(input, message);
    }
}
