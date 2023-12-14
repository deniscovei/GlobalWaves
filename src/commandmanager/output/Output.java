package commandmanager.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commandmanager.IoEntry;
import commandmanager.input.attributes.Stats;
import commandmanager.input.Input;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@JsonPropertyOrder({ "command", "user", "timestamp", "message", "results", "stats"})
public final class Output extends IoEntry {
    private final String user;
    private String message = null;
    private List<String> results = null;
    private List<?> result = null;
    private Stats stats = null;

    public Output(final Input input) {
        super(input.getCommand(), input.getTimestamp());
        this.user = input.getUsername();
    }

    public Output(final Input input, final String message) {
        this(input);
        this.message = message;
    }

    public Output(final Input input, final List<String> results) {
        this(input);
        this.results = results;
    }

    public Output(final Input input, final String message, final List<String> results) {
        this(input, message);
        this.results = results;
    }

    public Output(final Input input, final List<?> result, final Object magicParameter) {
        this(input);
        this.result = result;
    }

    public Output(final Input input, final Stats stats) {
        this(input);
        this.stats = stats;
    }
}
