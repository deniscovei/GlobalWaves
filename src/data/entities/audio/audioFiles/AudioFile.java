package data.entities.audio.audioFiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public AudioFile(String name) {
        super(name);
    }

    public AudioFile(String name, int duration) {
        this(name);
        this.duration = duration;
    }

    public static ArrayList <String> getFileNames(ArrayList <File> audioFiles) {
        ArrayList <String> nameList = new ArrayList<>();
        for (File audioFile : audioFiles) {
            nameList.add(audioFile.getName());
        }
        return nameList;
    }
}
