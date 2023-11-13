package commands.derived.output;

import commands.base.Command;
import com.fasterxml.jackson.annotation.JsonInclude;
import commands.derived.input.attributes.Stats;
import commands.derived.input.inputCommand.InputCommand;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class OutputCommand extends Command {
    private String user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<String> results = null;

    public OutputCommand(InputCommand inputCommand) {
        super(inputCommand.getCommand(), inputCommand.getTimestamp());
        this.user = inputCommand.getUsername();
    }

    public OutputCommand(InputCommand inputCommand, final String message) {
        this(inputCommand);
        this.message = message;
    }

    public OutputCommand(InputCommand inputCommand, final String message, final ArrayList<String> results) {
        this(inputCommand, message);
        this.results = results;
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

    public void addResult(String result) {
        this.results.add(result);
    }
}
