package CommandTypes;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class Command {
    protected String command;
    protected Integer timestamp;

    public Command() {
        this.command = null;
        this.timestamp = null;
    }

    public Command(final String command, final Integer timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }

    public final void setCommand(final String command) {
        this.command = command;
    }

    public final void setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
    }
}
