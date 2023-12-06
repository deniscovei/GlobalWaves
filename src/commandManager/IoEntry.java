package commandManager;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class IoEntry {
    protected String command = null;
    protected int timestamp = 0;

    public IoEntry() {
    }

    public IoEntry(final String command, final Integer timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }
}
