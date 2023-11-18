package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;

public final class PlayPauseStrategy implements CommandStrategy {
    public Output action(Input inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        String message;

        if (!user.hasLoadedAFile()) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else if (/*!user.getPlayer().hasFinished(inputCommand.getTimestamp()) && */!user.getPlayer().isPaused()) {
            user.getPlayer().pause(inputCommand.getTimestamp());
            message = "Playback paused successfully.";
        } else {
            user.getPlayer().play(inputCommand.getTimestamp());
            message = "Playback resumed successfully.";
        }

        return new Output(inputCommand, message);
    }
}
