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
import utils.Extras;
import utils.Extras.FileType;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class Album extends AudioCollection {
    private int releaseYear = 0;
    private String description = null;

    public Album(final String name, final String owner, final int releaseYear, final String description,
                 final ArrayList<SongInput> songs) {
        super(name, owner);
        this.fileType = FileType.ALBUM;
        this.releaseYear = releaseYear;
        this.description = description;
        for (SongInput songInput : songs) {
            this.audioFiles.add(new Song(songInput, true));
        }
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
                if (loadedFile.equals(this)
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
        }

        return false;
    }

    public int getNumberOfLikes() {
        int numberOfLikes = 0;
        for (AudioFile audioFile : getAudioFiles()) {
            Song song = (Song) audioFile;
            numberOfLikes += song.getNumberOfLikes();
        }

        return numberOfLikes;
    }
}
