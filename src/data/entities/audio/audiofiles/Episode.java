package data.entities.audio.audiofiles;

import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

@Setter
@Getter
public final class Episode extends AudioFile {
    private String description;

    public Episode(final EpisodeInput episode) {
        super(episode.getName(), episode.getDuration());
        this.fileType = Constants.FileType.EPISODE;
        this.description = episode.getDescription();
    }
}
