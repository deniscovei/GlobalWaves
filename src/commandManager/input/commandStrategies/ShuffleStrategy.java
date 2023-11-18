package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;
import utils.Constants;

public class ShuffleStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before using the shuffle function.";
        } else if (!user.getLoadedFile().getFileType().equals(Constants.FileType.PLAYLIST)){
            message = "The loaded source is not a playlist.";
        } else {
            if (user.getPlayer().isShuffleActivated()) {
                user.getPlayer().unshuffle(input.getTimestamp());
                message = "Shuffle function deactivated successfully.";
            } else {
                user.getPlayer().shuffle(input.getSeed(), input.getTimestamp());
                message = "Shuffle function activated successfully.";
            }
        }

        return new Output(input, message);
    }
}
