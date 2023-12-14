package data.entities.audio.audioFiles;

import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.FileType;

@Setter
@Getter
public final class Episode extends AudioFile {
    private String description;

    public Episode(final EpisodeInput episode) {
        super(episode.getName(), episode.getDuration());
        this.fileType = FileType.EPISODE;
        this.description = episode.getDescription();
    }
}
