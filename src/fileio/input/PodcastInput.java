package fileio.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class PodcastInput {
    private String name;
    private String owner;
    private List<EpisodeInput> episodes;
}
