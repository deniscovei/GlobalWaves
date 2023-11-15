package commandManager.input.commandStrategies;

import commandManager.input.attributes.Stats;
import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.user.User;

public final class StatusStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        Stats stats = new Stats();

        if (user.isLoaded() && user.getSelection().getRemainedTime(inputCommand.getTimestamp()) > 0) {
            stats.setName(user.getSelection().getName());
            stats.setRemainedTime(user.getSelection().getRemainedTime(inputCommand.getTimestamp()));
            stats.setPaused(!user.getSelection().isPlaying());
        } else {
            stats.setPaused(true);
        }

        return new OutputCommand(inputCommand, stats);
    }
}
