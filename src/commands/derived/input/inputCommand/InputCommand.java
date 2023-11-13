package commands.derived.input.inputCommand;

import commands.base.Command;
import commands.derived.input.attributes.Filters;
import commands.derived.input.attributes.Stats;
import commands.derived.input.commandTypes.*;
import commands.derived.output.OutputCommand;
import lombok.Getter;

@Getter
public class InputCommand extends Command {
    private String username = null;
    private String type = null;
    private Filters filters = null;
    private int itemNumber = 0;
    private int seed = 0;
    private int playlistId = 0;
    private String playlistName = null;

    public InputCommand() {
    }

    public InputCommand(String command, String username, Integer timestamp) {
        super(command, timestamp);
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public OutputCommand action() {
        switch (getCommand()) {
            case "search":
                return Search.action(this);
            case "select":
                return Select.action(this, itemNumber);
            case "load":
                return Load.action(this);
            case "playPause":
                return PlayPause.action(this);
            case "Status":
                return Status.action(this);
        }
        return null;
    }
}
