package data.entities.audio;

import lombok.Getter;

@Getter
public abstract class File {
    protected String name = null;

    public File() {

    }

    public File(String name) {
        this.name = name;
    }

    public abstract void play(int timestamp);

    public abstract boolean isPlaying();

    public abstract void pause(int timestamp);

    public abstract int getRemainedTime(int timestamp);
}
