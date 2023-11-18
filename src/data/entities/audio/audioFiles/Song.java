package data.entities.audio.audioFiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.user.User;
import fileio.input.SongInput;
import lombok.Getter;
import utils.Constants;

import java.util.ArrayList;

@JsonIgnoreType
@Getter
public class Song extends AudioFile {
    @JsonIgnore
    private String album = null;
    @JsonIgnore
    private ArrayList<String> tags = new ArrayList<>();
    @JsonIgnore
    private String lyrics = null;
    @JsonIgnore
    private String genre = null;
    @JsonIgnore
    private Integer releaseYear = null;
    @JsonIgnore
    private String artist = null;
    @JsonIgnore
    private final ArrayList <User> userWhoLiked = new ArrayList<>();

    public Song() {
        this.fileType = Constants.FileType.SONG;
    }

    public Song(final SongInput song) {
        super(song.getName(), song.getDuration());
        this.fileType = Constants.FileType.SONG;
        this.album = song.getAlbum();
        this.tags.addAll(song.getTags());
        this.lyrics = song.getLyrics();
        this.genre = song.getGenre();
        this.releaseYear = song.getReleaseYear();
        this.artist = song.getArtist();
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public void setAlbum(final String album) {
        this.album = album;
    }

    public void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public void registerLike(final User user) {
        userWhoLiked.add(user);
    }

    public void registerUnlike(final User user) {
        userWhoLiked.remove(user);
    }
}
