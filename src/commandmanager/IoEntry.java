package commandmanager;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Io entry.
 */
@Setter
@Getter
public abstract class IoEntry {
    /**
     * The Command.
     */
    protected String command = null;
    /**
     * The Timestamp.
     */
    protected int timestamp = 0;

    /**
     * Instantiates a new Io entry.
     */
    protected IoEntry() {
    }

    /**
     * Instantiates a new Io entry.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    protected IoEntry(final String command, final Integer timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }
}
