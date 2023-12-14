package data.entities.audio.audioCollections;

import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.FileType;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Album extends AudioCollection {
    private int releaseYear = 0;
    private String description = null;

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
     * returns the number of likes of the album
     */
    public int getNumberOfLikes() {
        int numberOfLikes = 0;
        for (AudioFile audioFile : getAudioFiles()) {
            Song song = (Song) audioFile;
            numberOfLikes += song.getNumberOfLikes();
        }

        return numberOfLikes;
    }
}
