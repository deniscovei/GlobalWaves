package data.entities.files.audioFiles;

import data.entities.users.Listener;
import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.FileType;

/**
 * The type Episode.
 */
@Setter
@Getter
public final class Episode extends AudioFile {
    private String description;

    /**
     * Instantiates a new Episode.
     *
     * @param episode the episode
     */
    public Episode(final EpisodeInput episode) {
        super(episode.getName(), episode.getDuration());
        this.fileType = FileType.EPISODE;
        this.description = episode.getDescription();
    }

    public void listen(final Listener listener) {

    }
}
