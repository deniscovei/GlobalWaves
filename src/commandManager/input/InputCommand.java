package commandManager.input;

import commandManager.Command;
import commandManager.input.attributes.Filters;
import commandManager.input.commandStrategies.CommandStrategy;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InputCommand extends Command {
    private String username = null;
    private String type = null;
    private Filters filters = null;
    private int itemNumber = 0;
    private int seed = 0;
    private int playlistId = 0;
    private String playlistName = null;

    public InputCommand() {
    }

    public InputCommand(String command, String username, Integer timestamp) {
        super(command, timestamp);
        this.username = username;
    }

    public OutputCommand action() {
        return commandStrategyAction(getCommand());
    }

    private OutputCommand commandStrategyAction(String inputCommand) {
        try {
            String inputCommandMatch = inputCommand.substring(0, 1).toUpperCase() + inputCommand.substring(1);
            String packagePath = "commandManager.input.commandStrategies.";
            String strategyClassName = packagePath + inputCommandMatch + "Strategy";
            Class<?> strategyClass = Class.forName(strategyClassName);
            CommandStrategy strategy = (CommandStrategy) strategyClass.getDeclaredConstructor().newInstance();
            return strategy.action(this);
        } catch (Exception e) {
            System.out.println("Error: Command \"" + inputCommand + "\" not found.");
            return null;
        }
    }
}
