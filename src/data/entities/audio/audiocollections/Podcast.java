package data.entities.audio.audiocollections;

import data.entities.audio.audiofiles.AudioFile;
import data.entities.audio.audiofiles.Episode;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.Constants;

import java.util.ArrayList;

public final class Podcast extends AudioCollection {
    public Podcast() {
        this.fileType = Constants.FileType.PODCAST;
    }

    public Podcast(final PodcastInput podcast) {
        super(podcast.getName(), podcast.getOwner());
        this.fileType = Constants.FileType.PODCAST;
        this.audioFiles = new ArrayList<>();
        for (EpisodeInput episodeInput : podcast.getEpisodes()) {
            this.audioFiles.add(new Episode(episodeInput));
        }
    }

    public ArrayList<AudioFile> getEpisodes() {
        return getAudioFiles();
    }
}
