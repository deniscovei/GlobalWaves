package data.entities.audio.audioCollections;

import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Episode;
import data.entities.audio.audioFiles.Song;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.Extras;
import utils.Extras.FileType;

import java.util.ArrayList;
import java.util.Objects;

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
        this.added = true;
    }

    public ArrayList<AudioFile> getEpisodes() {
        return getAudioFiles();
    }

    public boolean interactingWithOthers(int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == Extras.UserType.LISTENER) {
                Listener listener = (Listener) user;
                if (!Objects.requireNonNull(listener).hasLoadedAFile()
                        || listener.getPlayer().hasFinished(timestamp)) {
                    continue;
                }

                File loadedFile = listener.getPlayer().getLoadedFile();
                if (loadedFile.equals(this)) {
                    return true;
                }
            }
        }

        return false;
    }
}
