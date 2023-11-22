package data.entities.audio.audiofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import data.entities.audio.File;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@JsonIgnoreProperties({"duration", "startTimestamp", "pauseTimestamp", "offset"})
@Setter
@Getter
public abstract class AudioFile extends File {
    protected int duration = 0;

    public AudioFile() {
    }

    public AudioFile(final String name) {
        super(name);
    }

    public AudioFile(final String name, final int duration) {
        this(name);
        this.duration = duration;
    }

    /**
     * return the names of the files in the given list
     */
    public static ArrayList<String> getFileNames(final ArrayList<File> audioFiles) {
        ArrayList<String> nameList = new ArrayList<>();
        for (File audioFile : audioFiles) {
            nameList.add(audioFile.getName());
        }
        return nameList;
    }
}
