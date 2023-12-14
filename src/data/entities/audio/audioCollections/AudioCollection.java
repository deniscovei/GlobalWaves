package data.entities.audio.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AudioCollection extends File {
    @JsonIgnore
    protected String owner = null;
    @JsonIgnore
    protected List<AudioFile> audioFiles = new ArrayList<>();

    protected AudioCollection() {
        super();
    }

    protected AudioCollection(final String name) {
        super(name);
    }

    protected AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    protected AudioCollection(final String name,
                              final String owner,
                              final List<AudioFile> audioFiles) {
        this(name, owner);
        this.audioFiles = audioFiles;
    }
}
