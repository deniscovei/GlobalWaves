package data.entities.audio.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import utils.AudioFileListSerializer;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.FileType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public final class Playlist extends AudioCollection {
    private String visibility = AppUtils.PUBLIC;
    @JsonIgnore
    private final List<String> followerNames = new ArrayList<>();
    @JsonIgnore
    private int createdAt = 0;

    public Playlist() {
        this.fileType = FileType.PLAYLIST;
    }

    public Playlist(final String name, final String owner, final int createdAt) {
        super(name, owner);
        this.fileType = FileType.PLAYLIST;
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
    public List<AudioFile> getSongs() {
        return getAudioFiles();
    }

    private void makePublic() {
        setVisibility(AppUtils.PUBLIC);
    }

    private void makePrivate() {
        setVisibility(AppUtils.PRIVATE);
    }

    /**
     * switches the visibility of the playlist
     */
    public void switchVisibility() {
        if (getVisibility().equals(AppUtils.PUBLIC)) {
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
