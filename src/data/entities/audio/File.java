package data.entities.audio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import utils.Constants;

@Getter
public abstract class File {
    protected String name = null;
    @JsonIgnore
    protected Constants.FileType fileType = null;

    public File() {

    }

    public File(String name) {
        this.name = name;
    }
}
