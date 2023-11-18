package commandManager.input;

import commandManager.IO_Entry;
import commandManager.input.attributes.Filters;
import commandManager.input.commandStrategies.CommandStrategy;
import commandManager.output.Output;
import data.Database;
import data.entities.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Input extends IO_Entry {
    private String username = null;
    private String type = null;
    private Filters filters = null;
    private int itemNumber = 0;
    private int seed = 0;
    private int playlistId = 0;
    private String playlistName = null;

    public Input() {
    }

    public Input(String command, String username, Integer timestamp) {
        super(command, timestamp);
        this.username = username;
    }

    public Output action() {
        Output output = commandStrategyAction(getCommand());
        User user = Database.getInstance().findUser(getUsername());
        if (user != null) {
            user.setPreviousCommand(getCommand());
        }
        return output;
    }

    private Output commandStrategyAction(String inputCommand) {
        try {
            String inputCommandMatch = inputCommand.substring(0, 1).toUpperCase() + inputCommand.substring(1);
            String packagePath = "commandManager.input.commandStrategies.";
            String strategyClassName = packagePath + inputCommandMatch + "Strategy";
            Class<?> strategyClass = Class.forName(strategyClassName);
            CommandStrategy strategy = (CommandStrategy) strategyClass.getDeclaredConstructor().newInstance();
            return strategy.action(this);
        } catch (Exception e) {
            if (e instanceof NullPointerException)
                System.out.println("Null pointer exception at line " + e.getStackTrace()[0].getLineNumber() +
                                   " in file " + e.getStackTrace()[0].getFileName());
            System.out.println("Error: Command \"" + inputCommand + "\" not found or failed.");
            //e.printStackTrace();
            return null;
        }
    }
}
