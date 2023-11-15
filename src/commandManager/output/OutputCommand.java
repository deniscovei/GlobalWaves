package commandManager.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commandManager.Command;
import commandManager.input.attributes.Stats;
import commandManager.input.InputCommand;
import data.entities.audio.File;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@JsonPropertyOrder({ "command", "user", "timestamp", "message", "results", "stats"})
public class OutputCommand extends Command {
    private final String user;
    private String message = null;
    private ArrayList<String> results = null;
    private ArrayList<Object> result = null;
    private Stats stats = null;

    public OutputCommand(InputCommand inputCommand) {
        super(inputCommand.getCommand(), inputCommand.getTimestamp());
        this.user = inputCommand.getUsername();
    }

    public OutputCommand(InputCommand inputCommand, final String message) {
        this(inputCommand);
        this.message = message;
    }

    public OutputCommand(InputCommand inputCommand, final ArrayList<String> results) {
        this(inputCommand);
        this.results = results;
    }

    public OutputCommand(InputCommand inputCommand, final String message, final ArrayList<String> results) {
        this(inputCommand, message);
        this.results = results;
    }

    public OutputCommand(InputCommand inputCommand, final ArrayList<Object> result, Object garbage) {
        this(inputCommand);
        this.result = result;
    }

    public OutputCommand(InputCommand inputCommand, final Stats stats) {
        this(inputCommand);
        this.stats = stats;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResults(ArrayList<String> results) {
        this.results.addAll(results);
    }

    public void addResult(File result) {
        this.result.add(result);
    }

    public void setResult(ArrayList<Object> result) {
        this.result = result;
    }

    public final void setStats(final Stats stats) {
        this.stats = stats;
    }
}
