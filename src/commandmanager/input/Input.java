package commandmanager.input;

import commandmanager.IO_Entry;
import commandmanager.input.attributes.Filters;
import commandmanager.input.commandstrategies.CommandStrategy;
import commandmanager.output.Output;
import data.Database;
import data.entities.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class Input extends IO_Entry {
    private String username = null;
    private String type = null;
    private Filters filters = null;
    private int itemNumber = 0;
    private int seed = 0;
    private int playlistId = 0;
    private String playlistName = null;

    public Input() {
    }

    public Input(final String command, final String username, final Integer timestamp) {
        super(command, timestamp);
        this.username = username;
    }

    /**
     * This method calls the action method of the command strategy that matches the command.
     * @return the output of the command strategy
     */
    public Output action() {
        Output output = commandStrategyAction(getCommand());
        User user = Database.getInstance().findUser(getUsername());
        if (user != null) {
            user.setPreviousCommand(getCommand());
        }
        return output;
    }

    /**
     * This method calls the action method of the command strategy that matches the command.
     * @param input the command to be executed
     * @return the output of the command strategy
     */
    private Output commandStrategyAction(final String input) {
        try {
            final String inputMatch = input.substring(0, 1).toUpperCase() + input.substring(1);
            final String packagePath = "commandmanager.input.commandstrategies.";
            final String strategyClassName = packagePath + inputMatch + "Strategy";
            final Class<?> strategyClass = Class.forName(strategyClassName);
            final CommandStrategy strategy = (CommandStrategy) strategyClass.
                    getDeclaredConstructor().newInstance();

            return strategy.action(this);
        } catch (Exception e) {
            System.out.println("Error: Command \"" + input + "\" not found or failed.");

            if (e instanceof NullPointerException) {
                System.out.println("Null pointer exception at line "
                        + e.getStackTrace()[0].getLineNumber()
                        + " in file " + e.getStackTrace()[0].getFileName());
            }
            e.printStackTrace();

            return null;
        }
    }
}
