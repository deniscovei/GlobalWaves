package data.entities.audio.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.user.User;
import lombok.Getter;

@Getter
public abstract class AudioCollection extends File {
    @JsonIgnore
    protected String owner = null;

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
}
