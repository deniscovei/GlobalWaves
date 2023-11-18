package data.entities.audio.audioFiles;

import data.entities.audio.audioFiles.AudioFile;
import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

@Setter
@Getter
public class Episode extends AudioFile {
    private String description;

    public Episode() {
        this.fileType = Constants.FileType.EPISODE;
    }

    public Episode(EpisodeInput episode) {
        super(episode.getName(), episode.getDuration());
        this.fileType = Constants.FileType.EPISODE;
        this.description = episode.getDescription();
    }
}
