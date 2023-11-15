package data.entities.audio.audioFiles;

import data.entities.audio.audioFiles.AudioFile;
import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Episode extends AudioFile {
    private String description;

    public Episode() {
    }

    public Episode(EpisodeInput episode) {
        super(episode.getName(), episode.getDuration());
        this.description = episode.getDescription();
    }
}
