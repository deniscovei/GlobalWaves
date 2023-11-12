package CommandTypes.output;

import CommandTypes.Command;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class OutputCommand extends Command {
    private String user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<String> results;

    public OutputCommand(final String command, final String user, final int timestamp, boolean hasMessage, boolean hasResults) {
        super(command, timestamp);
        this.user = user;
        this.message = hasMessage ? "" : null;
        this.results = hasResults ? new ArrayList<>() : null;
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
