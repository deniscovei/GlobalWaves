package data.entities.audio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import utils.Extras.FileType;

@Getter
public abstract class File {
    protected String name = null;
    @JsonIgnore
    protected FileType fileType = null;
    @JsonIgnore
    protected boolean added = false;

    public File() {

    }

    public File(final String name) {
        this.name = name;
    }
}
