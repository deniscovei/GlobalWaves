package commandmanager;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class IO_Entry {
    protected String command = null;
    protected int timestamp = 0;

    public IO_Entry() {
    }

    public IO_Entry(final String command, final Integer timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }
}
