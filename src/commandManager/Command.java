package commandManager;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Command {
    protected String command = null;
    protected int timestamp = 0;

    public Command() {
    }

    public Command(final String command, final Integer timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }
}
