package data.entities.audio.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String visibility = Constants.PUBLIC;
    @JsonIgnore
    private ArrayList<String> followerNames = new ArrayList<>();
    @JsonIgnore
    private int createdAt = 0;

    public Playlist() {
        this.fileType = Constants.FileType.PLAYLIST;
    }

    public Playlist(final String name, final String owner, int createdAt) {
        super(name, owner);
        this.fileType = Constants.FileType.PLAYLIST;
        this.createdAt = createdAt;
    }

    public int getFollowers() {
        return followerNames.size();
    }

    @JsonSerialize(using = AudioFileListSerializer.class)
    public ArrayList<AudioFile> getSongs() {
        return getAudioFiles();
    }

    public void makePublic() {
        this.visibility = Constants.PUBLIC;
    }

    public void makePrivate() {
        this.visibility = Constants.PRIVATE;
    }

    public void switchVisibility() {
        if (getVisibility().equals(Constants.PUBLIC)) {
            makePrivate();
        } else {
            makePublic();
        }
    }

    public void addSong(Song song) {
        getAudioFiles().add(song);
    }

    public void removeSong(Song song) {
        getAudioFiles().remove(song);
    }
}
