package data.entities.audio.audioFiles;

import data.entities.audio.audioFiles.AudioFile;
import data.entities.user.User;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Song extends AudioFile {
    private String album = null;
    private ArrayList<String> tags = new ArrayList<>();
    private String lyrics = null;
    private String genre = null;
    private Integer releaseYear = null;
    private String artist = null;
    private final ArrayList <User> userWhoLiked = new ArrayList<>();

    public Song() {
    }

    public Song(final SongInput song) {
        super(song.getName(), song.getDuration());
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
