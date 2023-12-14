package commandmanager.input;

import commandmanager.IoEntry;
import commandmanager.input.attributes.Filters;
import commandmanager.input.commands.Command;
import commandmanager.output.Output;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public final class Input extends IoEntry {
    private String username = null;
    private String type = null;
    private Filters filters = null;
    private int itemNumber = 0;
    private int seed = 0;
    private int playlistId = 0;
    private String playlistName = null;
    private int age = 0;
    private String city = null;
    private String name = null;
    private int releaseYear = 0;
    private String description = null;
    private List<SongInput> songs = new ArrayList<>();
    private String date = null;
    private int price = 0;
    private List<EpisodeInput> episodes = new ArrayList<>();
    private String nextPage = null;

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
        return commandStrategyAction(getCommand());
    }

    /**
     * This method calls the action method of the command strategy that matches the command.
     * @param input the command to be executed
     * @return the output of the command strategy
     */
    private Output commandStrategyAction(final String input) {
        try {
            final String inputMatch = input.substring(0, 1).toUpperCase() + input.substring(1);
            final String packagePath = "commandmanager.input.commands.";
            final String strategyClassName = packagePath + inputMatch;
            final Class<?> strategyClass = Class.forName(strategyClassName);
            final Command strategy = (Command) strategyClass.
                    getDeclaredConstructor().newInstance();

            return strategy.action(this);
        } catch (Exception e) {
            System.out.println("Error: Command \"" + input + "\" not found or failed.");

            e.printStackTrace();

            return null;
        }
    }
}
