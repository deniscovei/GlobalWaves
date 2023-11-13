package data.entities.audio.derived;

import data.entities.audio.base.AudioFile;
import fileio.input.EpisodeInput;
import lombok.Getter;

@Getter
public class Episode extends AudioFile {
    private String description;

    public Episode() {
    }

    public Episode(EpisodeInput episode) {
        super(episode.getName(), episode.getDuration());
        this.description = episode.getDescription();
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
