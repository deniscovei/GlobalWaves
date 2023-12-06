package commandManager.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commandManager.IoEntry;
import commandManager.input.attributes.Stats;
import commandManager.input.Input;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@JsonPropertyOrder({ "command", "user", "timestamp", "message", "results", "stats"})
public final class Output extends IoEntry {
    private final String user;
    private String message = null;
    private ArrayList<String> results = null;
    private ArrayList<?> result = null;
    private Stats stats = null;

    public Output(final Input input) {
        super(input.getCommand(), input.getTimestamp());
        this.user = input.getUsername();
    }

    public Output(final Input input, final String message) {
        this(input);
        this.message = message;
    }

    public Output(final Input input, final ArrayList<String> results) {
        this(input);
        this.results = results;
    }

    public Output(final Input input, final String message, final ArrayList<String> results) {
        this(input, message);
        this.results = results;
    }

    public Output(final Input input, final ArrayList<?> result, final Object magicParameter) {
        this(input);
        this.result = result;
    }

    public Output(final Input input, final Stats stats) {
        this(input);
        this.stats = stats;
    }
}
