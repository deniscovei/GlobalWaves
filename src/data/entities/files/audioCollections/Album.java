package data.entities.files.audioCollections;

import data.Database;
import data.entities.files.File;
import data.entities.files.audioFiles.AudioFile;
import data.entities.files.audioFiles.Song;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.FileType;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Album.
 */
@Getter
@Setter
public class Album extends AudioCollection {
    private int releaseYear = 0;
    private String description = null;

    /**
     * Instantiates a new Album.
     *
     * @param name        the name
     * @param owner       the owner
     * @param releaseYear the release year
     * @param description the description
     * @param songs       the songs
     */
    public Album(final String name, final String owner, final int releaseYear,
                 final String description, final List<SongInput> songs) {
        super(name, owner);
        this.fileType = FileType.ALBUM;
        this.releaseYear = releaseYear;
        this.description = description;
        for (SongInput songInput : songs) {
            this.audioFiles.add(new Song(songInput, true));
        }
    }

    /**
     * checks if the album is interacting with users
     *
     * @param timestamp the timestamp
     * @return the boolean
     */
    public boolean interactingWithOthers(final int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() != AppUtils.UserType.LISTENER) {
                continue;
            }

            Listener listener = (Listener) user;
            if (!Objects.requireNonNull(listener).hasLoadedAFile()
                || ((Listener) user).getPlayer().hasFinished(timestamp)) {
                continue;
            }

            File loadedFile = listener.getPlayer().getLoadedFile();
            if (Objects.requireNonNull(loadedFile).equals(this)
                || loadedFile.getFileType() == FileType.SONG
                && ((Song) loadedFile).getAlbum().equals(getName())) {
                return true;
            }

            if (loadedFile.getFileType() == FileType.PLAYLIST) {
                for (AudioFile audioFile : getAudioFiles()) {
                    Song song = (Song) audioFile;

                    if (song.getAlbum().equals(getName())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * calculate the total number of likes using multiple threads
     * returns the number of likes of the album
     *
     * @return the number of likes
     */
    public int getNumberOfLikes() {
        List<Song> songs = getAudioFiles().stream()
            .filter(Song.class::isInstance)
            .map(Song.class::cast)
            .toList();

        // Use available processors as the number of threads
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        try {
            return songs.parallelStream()
                .mapToInt(Song::getNumberOfLikes)
                .sum();
        } finally {
            executorService.shutdown();
        }
    }

    public void listen(final Listener listener) {
        getAudioFiles().get(0).listen(listener);
    }
}
