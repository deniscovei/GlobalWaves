package data.entities.files.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.entities.files.File;
import data.entities.files.audioFiles.AudioFile;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Audio collection.
 */
@Getter
public abstract class AudioCollection extends File {
    /**
     * The Owner.
     */
    @JsonIgnore
    protected String owner = null;
    /**
     * The Audio files.
     */
    @JsonIgnore
    protected List<AudioFile> audioFiles = new ArrayList<>();

    /**
     * Instantiates a new Audio collection.
     */
    protected AudioCollection() {
        super();
    }

    /**
     * Instantiates a new Audio collection.
     *
     * @param name the name
     */
    protected AudioCollection(final String name) {
        super(name);
    }

    /**
     * Instantiates a new Audio collection.
     *
     * @param name  the name
     * @param owner the owner
     */
    protected AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * Instantiates a new Audio collection.
     *
     * @param name       the name
     * @param owner      the owner
     * @param audioFiles the audio files
     */
    protected AudioCollection(final String name,
                              final String owner,
                              final List<AudioFile> audioFiles) {
        this(name, owner);
        this.audioFiles = audioFiles;
    }
}
