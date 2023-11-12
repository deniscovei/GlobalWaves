package CommandTypes.input;

import CommandTypes.*;
import fileio.input.LibraryInput;
import CommandTypes.output.OutputCommand;
import lombok.Getter;

@Getter
public class InputCommand extends Command {
    private String username;
    private String type;
    private Filter filters;
    private Integer itemNumber;
    private Integer seed;
    private Integer playlistId;
    private Stat stats;
    private String playlistName;

    public InputCommand() {
        super();
        this.username = null;
        this.type = null;
        this.filters = new Filter();
        this.itemNumber = null;
        this.seed = null;
        this.playlistId = null;
        this.stats = new Stat();
        this.playlistName = null;
    }

    public InputCommand(String command, String username, Integer timestamp) {
        super(command, timestamp);
        this.username = username;
        this.type = null;
        this.filters = new Filter();
        this.itemNumber = null;
        this.seed = null;
        this.playlistId = null;
        this.stats = new Stat();
        this.playlistName = null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFilters(Filter filters) {
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

    public void setStats(Stat stats) {
        this.stats = stats;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public OutputCommand action(LibraryInput library) {
        switch (getCommand()) {
            case "search":
                return Search.action(this, library);
            case "select":
                return Select.action(this, itemNumber,library);
        }
        return null;
    }
}
