package data.entities.audio.audioCollections;

import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Episode;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Podcast extends AudioCollection {
    private ArrayList<Episode> episodes;

    public Podcast() {
    }

    public Podcast(final PodcastInput podcast) {
        super(podcast.getName(), podcast.getOwner());
        this.episodes = new ArrayList<>();
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public void setEpisodes(final ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public void play(int timestamp) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void pause(int timestamp) {

    }

    @Override
    public int getRemainedTime(int timestamp) {
        return 0;
    }
}
