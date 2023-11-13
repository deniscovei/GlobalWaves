package data.entities.audio.derived;

import data.entities.audio.base.AudioFile;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Song extends AudioFile {
    private String album;
    private ArrayList<String> tags = new ArrayList<>();
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;

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
}
