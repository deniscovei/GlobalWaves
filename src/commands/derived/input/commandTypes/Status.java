package commands.derived.input.commandTypes;

import commands.derived.input.attributes.Stats;
import commands.derived.input.inputCommand.InputCommand;
import commands.derived.output.OutputCommand;
import data.database.Database;
import data.entities.user.User;

public final class Status {
    public static OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        Stats stats = new Stats(user.getSelection().getName(),
                                user.getSelection().getRemainedTime(inputCommand.getTimestamp()));
        stats.setPaused(!user.getSelection().isPlaying());

        return new OutputCommand(inputCommand, stats);
    }
}
