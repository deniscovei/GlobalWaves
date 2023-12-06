package commandManager.input.attributes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Filters {
    private String name = null;
    private String album = null;
    private final ArrayList<String> tags = new ArrayList<>();
    private String lyrics = null;
    private String genre = null;
    private String releaseYear = null;
    private String artist = null;
    private String owner = null;
}
