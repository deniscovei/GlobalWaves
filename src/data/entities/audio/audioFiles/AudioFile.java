package data.entities.audio.audioFiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import data.entities.audio.File;
import lombok.Getter;

import java.util.ArrayList;

@JsonIgnoreProperties({"duration", "startTimestamp", "pauseTimestamp", "offset", "playing"})
@Getter
public abstract class AudioFile extends File {
    protected int duration = 0;
    protected int startTimestamp = -1;
    protected int pauseTimestamp = -1;
    protected int offset = 0;
    @JsonIgnore
    protected boolean playing = false;

    public AudioFile() {
    }

    public AudioFile(String name) {
        super(name);
    }

    public AudioFile(String name, int duration) {
        this(name);
        this.duration = duration;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public static ArrayList <String> getFileNames(ArrayList <File> audioFiles) {
        ArrayList <String> nameList = new ArrayList<>();
        for (File audioFile : audioFiles) {
            nameList.add(audioFile.getName());
        }
        return nameList;
    }

    public void setStartTimestamp(int startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public void setPauseTimestamp(int pauseTimestamp) {
        this.pauseTimestamp = pauseTimestamp;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean hasStarted() {
        return startTimestamp != -1;
    }

    public void play(int timestamp) {
        if (!hasStarted()) {
            setStartTimestamp(timestamp);
        } else {
            setOffset(getOffset() - timestamp + getPauseTimestamp());
        }
        setPlaying(true);
    }

    public void pause(int timestamp) {
        setPauseTimestamp(timestamp);
        setPlaying(false);
    }

    public int getCurrTime(int timestamp) {
        if (isPlaying()) {
            return timestamp - startTimestamp + offset;
        } else {
            return pauseTimestamp - startTimestamp + offset;
        }
    }

    public int getRemainedTime(int timestamp) {
        return duration - getCurrTime(timestamp);
    }
}
