package commandmanager.input.attributes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Stats {
    private String name = "";
    private int remainedTime = 0;
    private String repeat = "No Repeat";
    private boolean shuffle = false;
    private boolean paused = false;
}
