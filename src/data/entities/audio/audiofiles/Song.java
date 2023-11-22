package data.entities.audio.audiofiles;

import data.entities.user.User;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;

@Setter
@Getter
public final class Song extends AudioFile {
    private String album = null;
    private final ArrayList<String> tags = new ArrayList<>();
    private String lyrics = null;
    private String genre = null;
    private Integer releaseYear = null;
    private String artist = null;
    private final ArrayList<User> usersWhoLiked = new ArrayList<>();

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
}
