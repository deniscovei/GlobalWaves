package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import utils.Constants;
import data.Database;
import data.entities.user.User;

public class RepeatStrategy implements CommandStrategy {
    @Override
    public Output action(Input inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        String message;

        if (!user.hasLoadedAFile()) {
            message = "Please load a source before setting the repeat status.";
        } else {
            user.getPlayer().repeat();
            Constants.FileType fileType = user.getSelection().getFileType();
            int repeatState = user.getPlayer().getRepeatState();
            String repeatStateLowerCase = Constants.repeatStateToString(repeatState, fileType).toLowerCase();
            message = "Repeat mode changed to " + repeatStateLowerCase + ".";
        }

        return new Output(inputCommand, message);
    }
}
