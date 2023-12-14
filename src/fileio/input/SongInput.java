package fileio.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class SongInput {
    private String name;
    private Integer duration;
    private String album;
    private List<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;
}
