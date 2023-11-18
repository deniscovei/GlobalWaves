package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;
import utils.Constants;

public class BackwardStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user.getSelection() == null) {
            message = "Please select a file to load before skipping forward.";
        } else if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before skipping forward.";
        } else if (!user.getLoadedFile().getFileType().equals(Constants.FileType.PODCAST)) {
            message = "The loaded source is not a podcast.";
        } else {
            user.getPlayer().backward(input.getTimestamp());
            message = "Rewound successfully.";
        }

        return new Output(input, message);
    }
}
