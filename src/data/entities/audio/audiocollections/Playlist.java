package data.entities.audio.audiocollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import utils.AudioFileListSerializer;
import data.entities.audio.audiofiles.AudioFile;
import data.entities.audio.audiofiles.Song;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;

@Setter
@Getter
public final class Playlist extends AudioCollection {
    private String visibility = Constants.PUBLIC;
    @JsonIgnore
    private final ArrayList<String> followerNames = new ArrayList<>();
    @JsonIgnore
    private int createdAt = 0;

    public Playlist() {
        this.fileType = Constants.FileType.PLAYLIST;
    }

    public Playlist(final String name, final String owner, final int createdAt) {
        super(name, owner);
        this.fileType = Constants.FileType.PLAYLIST;
        this.createdAt = createdAt;
    }

    /**
     * returns the number of followers of the playlist
     */
    public int getFollowers() {
        return getFollowerNames().size();
    }

    /**
     * returns the number of songs in the playlist
     */
    @JsonSerialize(using = AudioFileListSerializer.class)
    public ArrayList<AudioFile> getSongs() {
        return getAudioFiles();
    }

    private void makePublic() {
        setVisibility(Constants.PUBLIC);
    }

    private void makePrivate() {
        setVisibility(Constants.PRIVATE);
    }

    /**
     * switches the visibility of the playlist
     */
    public void switchVisibility() {
        if (getVisibility().equals(Constants.PUBLIC)) {
            makePrivate();
        } else {
            makePublic();
        }
    }

    /**
     * adds a follower to the playlist
     */
    public void addSong(final Song song) {
        getAudioFiles().add(song);
    }

    /**
     * removes a follower from the playlist
     */
    public void removeSong(final Song song) {
        getAudioFiles().remove(song);
    }
}
