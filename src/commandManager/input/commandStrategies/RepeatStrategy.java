package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import utils.Constants;
import data.Database;
import data.entities.user.User;

public class RepeatStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!user.hasLoadedAFile()) {
            message = "Please load a source before setting the repeat status.";
        } else {
            System.out.println("timestamp: " + input.getTimestamp());
            System.out.println("Repeat mode is " + user.getPlayer().getRepeatState());
            user.getPlayer().repeat(input.getTimestamp());
            Constants.FileType fileType = user.getLoadedFile().getFileType();
            int repeatState = user.getPlayer().getRepeatState();
            String repeatStateLowerCase = Constants.repeatStateToString(repeatState, fileType).toLowerCase();
            message = "Repeat mode changed to " + repeatStateLowerCase + ".";
        }

        return new Output(input, message);
    }
}
