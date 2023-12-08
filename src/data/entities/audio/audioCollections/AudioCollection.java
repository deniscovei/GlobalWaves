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

    public AudioCollection(final String name) {
        super(name);
    }

    public AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    public AudioCollection(final String name,
                           final String owner,
                           final ArrayList<AudioFile> audioFiles) {
        this(name, owner);
        this.audioFiles = audioFiles;
    }

    public AudioFile findAudioFile(String name) {
        for (AudioFile audioFile : audioFiles) {
            if (audioFile.getName().equals(name)) {
                return audioFile;
            }
        }
        return null;
    }
}
