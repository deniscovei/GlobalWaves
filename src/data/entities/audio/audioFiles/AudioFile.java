package data.entities.audio.audioFiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import data.entities.audio.File;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Audio file.
 */
@JsonIgnoreProperties({"duration", "startTimestamp", "pauseTimestamp", "offset"})
@Setter
@Getter
public abstract class AudioFile extends File {
    /**
     * The Duration.
     */
    protected int duration = 0;

    /**
     * Instantiates a new Audio file.
     */
    protected AudioFile() {
    }

    /**
     * Instantiates a new Audio file.
     *
     * @param name the name
     */
    protected AudioFile(final String name) {
        super(name);
    }

    /**
     * Instantiates a new Audio file.
     *
     * @param name     the name
     * @param duration the duration
     */
    protected AudioFile(final String name, final int duration) {
        this(name);
        this.duration = duration;
    }
}
