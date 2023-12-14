package data.entities.audio.audioCollections;

import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Episode;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.AppUtils;
import utils.AppUtils.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Podcast.
 */
public final class Podcast extends AudioCollection {
    /**
     * Instantiates a new Podcast.
     */
    public Podcast() {
        this.fileType = FileType.PODCAST;
    }

    /**
     * Instantiates a new Podcast.
     *
     * @param podcast the podcast
     */
    public Podcast(final PodcastInput podcast) {
        super(podcast.getName(), podcast.getOwner());
        this.fileType = FileType.PODCAST;
        this.audioFiles = new ArrayList<>();
        for (EpisodeInput episodeInput : podcast.getEpisodes()) {
            this.audioFiles.add(new Episode(episodeInput));
        }
    }

    /**
     * Instantiates a new Podcast.
     *
     * @param name     the name
     * @param username the username
     * @param episodes the episodes
     */
    public Podcast(final String name, final String username, final List<EpisodeInput> episodes) {
        super(name, username);
        this.fileType = FileType.PODCAST;
        this.audioFiles = new ArrayList<>();
        for (EpisodeInput episodeInput : episodes) {
            this.audioFiles.add(new Episode(episodeInput));
        }
        this.added = true;
    }

    /**
     * Gets episodes.
     *
     * @return the episodes
     */
    public List<AudioFile> getEpisodes() {
        return getAudioFiles();
    }

    /**
     * checks if the podcast is interacting with users
     *
     * @param timestamp the timestamp
     * @return the boolean
     */
    public boolean interactingWithOthers(final int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == AppUtils.UserType.LISTENER) {
                Listener listener = (Listener) user;
                if (!Objects.requireNonNull(listener).hasLoadedAFile()
                        || listener.getPlayer().hasFinished(timestamp)) {
                    continue;
                }

                File loadedFile = listener.getPlayer().getLoadedFile();
                if (Objects.requireNonNull(loadedFile).equals(this)) {
                    return true;
                }
            }
        }

        return false;
    }
}
