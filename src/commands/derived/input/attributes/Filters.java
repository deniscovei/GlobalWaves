package commands.derived.input.attributes;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Filters {
    private String name = null;
    private String album = null;
    private ArrayList<String> tags = new ArrayList<>();;
    private String lyrics = null;
    private String genre = null;
    private String releaseYear = null;
    private String artist = null;
    private String owner = null;

    public Filters() {
    }

    public void setName(final String name) {
        this.name = name;
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

    public void setReleaseYear(final String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }
}
