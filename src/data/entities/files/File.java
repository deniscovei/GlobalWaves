package data.entities.files;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.entities.users.Listener;
import lombok.Getter;
import utils.AppUtils.FileType;

/**
 * The type File.
 */
@Getter
public abstract class File {
    /**
     * The Name.
     */
    protected String name = null;
    /**
     * The File type.
     */
    @JsonIgnore
    protected FileType fileType = null;
    /**
     * The Added.
     */
    @JsonIgnore
    protected boolean added = false;

    /**
     * Instantiates a new File.
     */
    public File() {

    }

    /**
     * Instantiates a new File.
     *
     * @param name the name
     */
    public File(final String name) {
        this.name = name;
    }

    /**
     * Listen.
     *
     * @param listener the listener
     */
    public abstract void listen(Listener listener);
}
