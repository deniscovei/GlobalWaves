package commands.derived.input.attributes;

import lombok.Getter;

@Getter
public class Stats {
    private String name = null;
    private int remainedTime = 0;
    private String repeat = "No Repeat";
    private boolean shuffle = false;
    private boolean paused = false;

    public Stats() {
    }

    public Stats(final String name, final int remainedTime) {
        this.name = name;
        this.remainedTime = remainedTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemainedTime(int remainedTime) {
        this.remainedTime = remainedTime;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
