package data.entities.audio.audioCollections;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import utils.AudioFileListSerializer;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;

@Setter
@Getter
public class Playlist extends AudioCollection {
    private String visibility = "public";
    private int followers = 0;

    public Playlist() {
        this.fileType = Constants.FileType.PLAYLIST;
    }

    public Playlist(final String name) {
        super(name);
        this.fileType = Constants.FileType.PLAYLIST;
    }

    public Playlist(final String name, final String owner) {
        super(name);
        this.fileType = Constants.FileType.PLAYLIST;
        this.owner = owner;
    }

    @JsonSerialize(using = AudioFileListSerializer.class)
    public ArrayList <AudioFile> getSongs() {
        return getAudioFiles();
    }

    public void makePublic() {
        this.visibility = "public";
    }

    public void makePrivate() {
        this.visibility = "private";
    }

    public void addSong(Song song) {
        getAudioFiles().add(song);
    }

    public void removeSong(Song song) {
        getAudioFiles().remove(song);
    }
}
