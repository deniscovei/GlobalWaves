package data.entities.audio.audioCollections;

import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Episode;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.Extras.FileType;

import java.util.ArrayList;

public final class Podcast extends AudioCollection {
    public Podcast() {
        this.fileType = FileType.PODCAST;
    }

    public Podcast(final PodcastInput podcast) {
        super(podcast.getName(), podcast.getOwner());
        this.fileType = FileType.PODCAST;
        this.audioFiles = new ArrayList<>();
        for (EpisodeInput episodeInput : podcast.getEpisodes()) {
            this.audioFiles.add(new Episode(episodeInput));
        }
    }

    public Podcast(String name, String username, ArrayList<EpisodeInput> episodes) {
        super(name, username);
        this.fileType = FileType.PODCAST;
        this.audioFiles = new ArrayList<>();
        for (EpisodeInput episodeInput : episodes) {
            this.audioFiles.add(new Episode(episodeInput));
        }
    }

    public ArrayList<AudioFile> getEpisodes() {
        return getAudioFiles();
    }
}
