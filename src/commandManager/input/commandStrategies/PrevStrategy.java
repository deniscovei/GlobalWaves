package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;

public class PrevStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        boolean prevSucceded = user.hasLoadedAFile() &&
                               !user.getPlayer().hasFinished(input.getTimestamp()) &&
                               user.getPlayer().prev(input.getTimestamp());

        if (prevSucceded) {
            message = "Returned to previous track successfully. The current track is " +
                    user.getPlayer().getCurrentPlayingFile(input.getTimestamp()).getName() + ".";
        } else {
            message = "Please load a source before returning to the previous track.";
        }

        return new Output(input, message);
    }
}
