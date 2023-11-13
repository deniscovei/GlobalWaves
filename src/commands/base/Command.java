package commands.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import commands.derived.input.attributes.Stats;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class Command {
    protected String command = null;
    protected int timestamp = 0;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Stats stats = null;

    public Command() {
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

    public final void setStats(final Stats stats) {
        this.stats = stats;
    }
}
