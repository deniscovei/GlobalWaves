package data.entities.audio.audioFiles;

import data.entities.users.User;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.FileType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public final class Song extends AudioFile {
    private String album = null;
    private final List<String> tags = new ArrayList<>();
    private String lyrics = null;
    private String genre = null;
    private Integer releaseYear = null;
    private String artist = null;
    private final List<User> usersWhoLiked = new ArrayList<>();

    public Song() {
        this.fileType = FileType.SONG;
    }

    public Song(final SongInput song) {
        super(song.getName(), song.getDuration());
        this.fileType = FileType.SONG;
        this.album = song.getAlbum();
        this.tags.addAll(song.getTags());
        this.lyrics = song.getLyrics();
        this.genre = song.getGenre();
        this.releaseYear = song.getReleaseYear();
        this.artist = song.getArtist();
    }

    public Song(final SongInput songInput, final boolean added) {
        this(songInput);
        this.added = added;
    }

    /**
     * adds a user to the list of users who liked the song
     */
    public void registerLike(final User user) {
        getUsersWhoLiked().add(user);
    }

    /**
     * removes a user from the list of users who liked the song
     */
    public void registerUnlike(final User user) {
        getUsersWhoLiked().remove(user);
    }

    public int getNumberOfLikes() {
        return getUsersWhoLiked().size();
    }
}
