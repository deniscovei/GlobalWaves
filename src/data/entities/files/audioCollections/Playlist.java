package data.entities.files.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import data.entities.users.Listener;
import utils.AudioFileListSerializer;
import data.entities.files.audioFiles.AudioFile;
import data.entities.files.audioFiles.Song;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.FileType;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Playlist.
 */
@Setter
@Getter
public final class Playlist extends AudioCollection {
    private String visibility = AppUtils.PUBLIC;
    @JsonIgnore
    private final List<String> followerNames = new ArrayList<>();
    @JsonIgnore
    private int createdAt = 0;

    /**
     * Instantiates a new Playlist.
     */
    public Playlist() {
        this.fileType = FileType.PLAYLIST;
    }

    /**
     * Instantiates a new Playlist.
     *
     * @param name      the name
     * @param owner     the owner
     * @param createdAt the created at
     */
    public Playlist(final String name, final String owner, final int createdAt) {
        super(name, owner);
        this.fileType = FileType.PLAYLIST;
        this.createdAt = createdAt;
    }

    /**
     * returns the number of followers of the playlist
     *
     * @return the followers
     */
    public int getFollowers() {
        return getFollowerNames().size();
    }

    /**
     * returns the number of songs in the playlist
     *
     * @return the songs
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
     *
     * @param song the song
     */
    public void addSong(final Song song) {
        getAudioFiles().add(song);
    }

    /**
     * Add all songs.
     *
     * @param songs the songs
     */
    public void addAllSongs(final List<Song> songs) {
        getAudioFiles().addAll(songs);
    }

    /**
     * removes a follower from the playlist
     *
     * @param song the song
     */
    public void removeSong(final Song song) {
        getAudioFiles().remove(song);
    }

    public void listen(final Listener listener) {
        getAudioFiles().get(0).listen(listener);
    }
}
