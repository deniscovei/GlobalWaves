package data.entities.audio.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class AudioCollection extends File {
    @JsonIgnore
    protected String owner = null;
    @JsonIgnore
    protected ArrayList<AudioFile> audioFiles = new ArrayList<>();

    public AudioCollection() {
        super();
    }

    public AudioCollection(String name) {
        super(name);
    }

    public AudioCollection(String name, String owner) {
        super(name);
        this.owner = owner;
    }

    public AudioCollection(String name, String owner, ArrayList<AudioFile> audioFiles) {
        this(name, owner);
        this.audioFiles = audioFiles;
    }
}
