package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;

public final class PlayPauseStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else if (!user.getPlayer().isPaused()) {
            user.getPlayer().pause(input.getTimestamp());
            message = "Playback paused successfully.";
        } else {
            user.getPlayer().play(input.getTimestamp());
            message = "Playback resumed successfully.";
        }

        return new Output(input, message);
    }
}
