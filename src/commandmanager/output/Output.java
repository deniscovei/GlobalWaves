package commandmanager.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commandmanager.IoEntry;
import commandmanager.input.attributes.Stats;
import commandmanager.input.Input;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

/**
 * The type Output.
 */
@Setter
@Getter
@JsonPropertyOrder({ "command", "user", "timestamp", "message", "results", "stats"})
public final class Output extends IoEntry {
    private String user;
    private String message = null;
    private List<String> results = null;
    Object result = null;
    private Stats stats = null;

    public Output(final String command, final Object result) {
        this.command = command;
        this.result = result;
    }

    /**
     * Instantiates a new Output.
     *
     * @param input the input
     */
    public Output(final Input input) {
        super(input.getCommand(), input.getTimestamp());
        this.user = input.getUsername();
    }

    /**
     * Instantiates a new Output.
     *
     * @param input   the input
     * @param message the message
     */
    public Output(final Input input, final String message) {
        this(input);
        this.message = message;
    }

    /**
     * Instantiates a new Output.
     *
     * @param input   the input
     * @param results the results
     */
    public Output(final Input input, final List<String> results) {
        this(input);
        this.results = results;
    }

    /**
     * Instantiates a new Output.
     *
     * @param input   the input
     * @param message the message
     * @param results the results
     */
    public Output(final Input input, final String message, final List<String> results) {
        this(input, message);
        this.results = results;
    }

    /**
     * Instantiates a new Output.
     *
     * @param input          the input
     * @param result         the result
     * @param magicParameter the magic parameter
     */
    public Output(final Input input, final Object result, final Object magicParameter) {
        this(input);
        this.result = result;
    }

    /**
     * Instantiates a new Output.
     *
     * @param input the input
     * @param stats the stats
     */
    public Output(final Input input, final Stats stats) {
        this(input);
        this.stats = stats;
    }
}
