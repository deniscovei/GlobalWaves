package data.entities.audio.audioFiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import data.entities.audio.File;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"duration", "startTimestamp", "pauseTimestamp", "offset"})
@Setter
@Getter
public abstract class AudioFile extends File {
    protected int duration = 0;

    protected AudioFile() {
    }

    protected AudioFile(final String name) {
        super(name);
    }

    protected AudioFile(final String name, final int duration) {
        this(name);
        this.duration = duration;
    }
}
