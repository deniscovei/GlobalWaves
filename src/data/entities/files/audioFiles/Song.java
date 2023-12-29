package data.entities.files.audioFiles;

import data.Database;
import data.entities.users.Artist;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Song.
 */
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

    /**
     * Instantiates a new Song.
     */
    public Song() {
        this.fileType = FileType.SONG;
    }

    /**
     * Instantiates a new Song.
     *
     * @param song the song
     */
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

    /**
     * Instantiates a new Song.
     *
     * @param songInput the song input
     * @param added     the added
     */
    public Song(final SongInput songInput, final boolean added) {
        this(songInput);
        this.added = added;
    }

    /**
     * adds a user to the list of users who liked the song
     *
     * @param user the user
     */
    public void registerLike(final User user) {
        getUsersWhoLiked().add(user);
    }

    /**
     * removes a user from the list of users who liked the song
     *
     * @param user the user
     */
    public void registerUnlike(final User user) {
        getUsersWhoLiked().remove(user);
    }

    /**
     * Gets number of likes.
     *
     * @return the number of likes
     */
    public int getNumberOfLikes() {
        return getUsersWhoLiked().size();
    }

    static int count;

    public void listen(final Listener listener) {
        //System.out.println(++count + " Song: " + getName() + " from album " + getAlbum() + " "
         //+ listener.getUsername() + " " + getDuration());
        Listener.ListenerTops tops = (Listener.ListenerTops) listener.getTops();

        tops.getTopAlbums().compute(getAlbum(), (album, count) -> (count == null) ? 1 : count + 1);
        tops.getTopArtists().compute(getArtist(), (artist, count) ->
                (count == null) ? 1 : count + 1);
        tops.getTopGenres().compute(getGenre(), (genre, count) -> (count == null) ? 1 : count + 1);
        tops.getTopSongs().compute(getName(), (song, count) -> (count == null) ? 1 : count + 1);

        Artist artist = (Artist) Database.getInstance().findUser(getArtist());

        if (artist == null) {
            artist = new Artist(getArtist());
            Database.getInstance().addUser(artist);
        }

        Artist.ArtistTops artistTops = (Artist.ArtistTops) Objects.requireNonNull(artist).getTops();

        artistTops.getTopAlbums()
                .compute(getAlbum(), (album, count) -> (count == null) ? 1 : count + 1);
        artistTops.getTopSongs()
                .compute(getName(), (song, count) -> (count == null) ? 1 : count + 1);
        if (!artistTops.getTopFans().contains(listener.getUsername())) {
            artistTops.getTopFans().add(listener.getUsername());
            artistTops.setListeners(artistTops.getListeners() + 1);
        }
    }
}
