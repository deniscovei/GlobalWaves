package data.entities.audio.derived;

import data.entities.audio.base.AudioFile;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Podcast extends AudioFile {
    private String owner;
    private ArrayList<Episode> episodes;

    public Podcast() {
    }

    public Podcast(final PodcastInput podcast) {
        super(podcast.getName());
        this.owner = podcast.getOwner();
        this.episodes = new ArrayList<>();
        for (EpisodeInput episode : podcast.getEpisodes()) {
            this.duration += episode.getDuration();
            this.episodes.add(new Episode(episode));
        }
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public void setEpisodes(final ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}
