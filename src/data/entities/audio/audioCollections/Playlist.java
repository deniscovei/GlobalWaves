package data.entities.audio.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import utils.AudioFileListSerializer;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import lombok.Getter;
import lombok.Setter;
import utils.Extras;
import utils.Extras.FileType;

import java.util.ArrayList;

@Setter
@Getter
public final class Playlist extends AudioCollection {
    private String visibility = Extras.PUBLIC;
    @JsonIgnore
    private final ArrayList<String> followerNames = new ArrayList<>();
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
    public ArrayList<AudioFile> getSongs() {
        return getAudioFiles();
    }

    private void makePublic() {
        setVisibility(Extras.PUBLIC);
    }

    private void makePrivate() {
        setVisibility(Extras.PRIVATE);
    }

    /**
     * switches the visibility of the playlist
     */
    public void switchVisibility() {
        if (getVisibility().equals(Extras.PUBLIC)) {
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
