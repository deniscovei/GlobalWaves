package data.entities.files.audioFiles;

import data.Database;
import data.entities.files.audioCollections.Album;
import data.entities.users.contentCreator.Artist;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Song.
 */
@Setter
@Getter
public class Song extends AudioFile {
    /**
     * The Album.
     */
    protected String album = null;
    /**
     * The Tags.
     */
    protected final List<String> tags = new ArrayList<>();
    /**
     * The Lyrics.
     */
    protected String lyrics = null;
    /**
     * The Genre.
     */
    protected String genre = null;
    /**
     * The Release year.
     */
    protected Integer releaseYear = null;
    /**
     * The Artist.
     */
    protected String artist = null;
    /**
     * The Users who liked.
     */
    protected final List<User> usersWhoLiked = new ArrayList<>();

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

    public void listen(final Listener listener) {
        Listener.ListenerTops tops = (Listener.ListenerTops) listener.getTops();

        tops.getTopAlbums().compute(getAlbum(), (topAlbum, count)
            -> (count == null) ? 1 : count + 1);
        tops.getTopArtists().compute(getArtist(), (topArtist, count)
            -> (count == null) ? 1 : count + 1);
        tops.getTopGenres().compute(getGenre(), (topGenre, count)
            -> (count == null) ? 1 : count + 1);
        tops.getTopSongs().compute(getName(), (topSong, count)
            -> (count == null) ? 1 : count + 1);

        Artist artist = null;
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == AppUtils.UserType.ARTIST) {
                Artist currArtist = (Artist) user;

                if (currArtist.getUsername().equals(getArtist())) {
                    for (Album currAlbum : currArtist.getAlbums()) {
                        if (currAlbum.getName().equals(getAlbum())) {
                            artist = currArtist;
                            break;
                        }
                    }
                }

                if (artist != null) {
                    break;
                }

            }
        }

        if (artist == null) {
            artist = new Artist(getArtist());
            Database.getInstance().addUser(artist);
        }

        Artist.ArtistTops artistTops = (Artist.ArtistTops) Objects.requireNonNull(artist).getTops();

        artistTops.getTopAlbums()
            .compute(getAlbum(), (topAlbum, count) -> (count == null) ? 1 : count + 1);
        artistTops.getTopSongs()
            .compute(getName(), (topSong, count) -> (count == null) ? 1 : count + 1);
        if (!artistTops.getTopFans().contains(listener.getUsername())) {
            artistTops.getTopFans().add(listener.getUsername());
            artistTops.setListeners(artistTops.getListeners() + 1);
        }

        if (listener.isPremium()) {
            listener.setPremiumListens(listener.getPremiumListens() + 1);

            if (!listener.getListensPerSong().containsKey(this)) {
                listener.getListensPerSong().put(this, 1);
            } else {
                listener.getListensPerSong().put(this,
                    listener.getListensPerSong().get(this) + 1);
            }

            if (!artist.getListens().containsKey(listener)) {
                artist.getListens().put(listener, 1);
            } else {
                artist.getListens().put(listener, artist.getListens().get(listener) + 1);
            }
        }

        listener.setTotalListens(listener.getTotalListens() + 1);
        listener.getHistory().add(this);
    }
}
