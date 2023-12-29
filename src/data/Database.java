package data;

import data.entities.files.audioCollections.Album;
import data.entities.files.audioCollections.Playlist;
import data.entities.files.audioCollections.Podcast;
import data.entities.files.audioFiles.Song;
import data.entities.users.Artist;
import data.entities.users.Host;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.UserType;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Database.
 */
@Setter
@Getter
public final class Database {
    private static Database instance;
    private final List<User> users = new ArrayList<>();
    private final List<Song> songs = new ArrayList<>();
    private final List<Podcast> podcasts = new ArrayList<>();
    private final List<Playlist> playlists = new ArrayList<>();
    private final List<Album> albums = new ArrayList<>();

    private Database() {
    }

    /**
     * returns the database instance
     *
     * @return the instance
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * returns true if the database has been instantiated
     *
     * @return the boolean
     */
    public static boolean instantiated() {
        return instance != null;
    }

    /**
     * returns the user with the given username
     *
     * @param username the username
     * @return the user
     */
    public User findUser(final String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * returns the playlist with the given name
     *
     * @param playlistName the playlist name
     * @return the playlist
     */
    public Playlist findPlaylist(final String playlistName) {
        for (Playlist playlist : getPlaylists()) {
            if (playlist.getName().equals(playlistName)) {
                return playlist;
            }
        }
        return null;
    }


    /**
     * returns the playlist with the given id in the user's playlist list
     *
     * @param playlistId the playlist id
     * @param owner      the owner
     * @return the playlist
     */
    public Playlist findPlaylist(final int playlistId, final String owner) {
        int currentId = 0;
        for (Playlist playlist : getPlaylists()) {
            if (playlist.getOwner().equals(owner)) {
                currentId++;
                if (currentId == playlistId) {
                    return playlist;
                }
            }
        }
        return null;
    }

    /**
     * adds a user to the database
     *
     * @param user the user
     */
    public void addUser(final User user) {
        getUsers().add(user);
    }

    /**
     * adds a song to the database
     *
     * @param song the song
     */
    public void addSong(final Song song) {
        getSongs().add(song);
    }

    /**
     * adds a podcast to the database
     *
     * @param podcast the podcast
     */
    public void addPodcast(final Podcast podcast) {
        getPodcasts().add(podcast);
    }

    /**
     * adds a playlist to the database
     *
     * @param playlist the playlist
     */
    public void addPlaylist(final Playlist playlist) {
        getPlaylists().add(playlist);
    }

    /**
     * uploads the library
     *
     * @param library the library
     */
    public void upload(final LibraryInput library) {
        for (UserInput user : library.getUsers()) {
            addUser(new Listener(user));
        }
        for (SongInput song : library.getSongs()) {
            addSong(new Song(song));
        }
        for (PodcastInput podcast : library.getPodcasts()) {
            addPodcast(new Podcast(podcast));
        }
    }

    /**
     * removes all the likes of the given user from the database
     *
     * @param listener the listener
     */
    public void removeLikes(final User listener) {
        for (Song song : getSongs()) {
            song.getUsersWhoLiked().remove(listener);
        }
    }

    /**
     * adds a new user to the database
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     * @param userType the user type
     */
    public void addUser(final String username, final int age, final String city,
                        final UserType userType) {
        User user = switch (userType) {
            case ARTIST -> new Artist(username, age, city);
            case HOST -> new Host(username, age, city);
            default -> new Listener(username, age, city);
        };
        user.setAdded(true);
        addUser(user);
    }

    /**
     * deletes added data
     */
    public void flush(final LibraryInput library) {
        users.clear();
        songs.clear();
        podcasts.clear();
        playlists.clear();
        albums.clear();
        upload(library);
    }

    /**
     * returns the followed playlists of the given user
     *
     * @param username the username
     * @return the followed playlists
     */
    public ArrayList<Playlist> getFollowedPlaylists(final String username) {
        ArrayList<Playlist> result = new ArrayList<>();

        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getFollowerNames().contains(username)) {
                result.add(playlist);
            }
        }

        return result;
    }

    /**
     * searches for the given song in the database
     *
     * @param songName the song name
     * @return the song
     */
    public Song findSong(final String songName) {
        for (Song song : getSongs()) {
            if (song.getName().equals(songName)) {
                return song;
            }
        }
        return null;

    }

    /**
     * searches for the given podcast in the database
     *
     * @param podcastName the podcast name
     * @return the podcast
     */
    public Podcast findPodcast(final String podcastName) {
        for (Podcast podcast : getPodcasts()) {
            if (podcast.getName().equals(podcastName)) {
                return podcast;
            }
        }
        return null;
    }

    /**
     * searches for the given album in the database
     *
     * @param albumName the album name
     * @return the album
     */
    public Album findAlbum(final String albumName) {
        for (Album album : getAlbums()) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }

    /**
     * removes the given user from the database
     *
     * @param user the user
     */
    public void removeUser(final User user) {
        switch (user.getUserType()) {
            case ARTIST:
                getUsers().remove(user);
                removeSongs(user);
                removeAlbums(user);
                break;
            case HOST:
                getUsers().remove(user);
                removePodcasts(user);
                break;
            default: // listener
                Listener listener = (Listener) user;
                if (user.isAdded()) {
                    getUsers().remove(user);
                } else {
                    listener.setDeleted(true);
                }
                removeFollowedPlaylists(listener);
                removePlaylists(listener);
                removeLikes(listener);
        }
    }

    /**
     * removes all the albums from the database
     */
    private void removeAlbums() {
        getAlbums().clear();
    }

    /**
     * removes albums of the given artist from the database
     */
    private void removeAlbums(final User artist) {
        for (int i = 0; i < getAlbums().size(); i++) {
            Album album = getAlbums().get(i);

            if (album.getOwner().equals(artist.getUsername())) {
                getAlbums().remove(i);
                i--;
            }
        }
    }

    /**
     * removes podcasts of the given host from the database
     */
    private void removePodcasts(final User host) {
        for (int i = 0; i < getPodcasts().size(); i++) {
            Podcast podcast = getPodcasts().get(i);

            if (podcast.getOwner().equals(host.getUsername())) {
                getPodcasts().remove(i);
                i--;
            }
        }
    }

    /**
     * removes followed playlists of the given listener from the database
     */
    private void removeFollowedPlaylists(final Listener listener) {
        for (int i = 0; i < getPlaylists().size(); i++) {
            getPlaylists().get(i).getFollowerNames().remove(listener.getUsername());
        }
    }

    /**
     * removes playlists of the given listener from the database
     */
    private void removePlaylists(final Listener listener) {
        for (int i = 0; i < getPlaylists().size(); i++) {
            Playlist playlist = getPlaylists().get(i);

            if (playlist.getOwner().equals(listener.getUsername())) {
                getPlaylists().remove(i);
                i--;
            }
        }
    }

    /**
     * removes songs of the given artist from the database
     */
    private void removeSongs(final User artist) {
        for (int i = 0; i < getSongs().size(); i++) {
            Song song = getSongs().get(i);

            if (song.getArtist().equals(artist.getUsername())) {
                getSongs().remove(i);
                i--;
            }
        }

        for (User user : getUsers()) {
            if (user.getUserType() == UserType.LISTENER) {
                Listener listener = (Listener) user;
                listener.getLikedSongs()
                    .removeIf(song -> song.getArtist().equals(artist.getUsername()));
            }
        }
    }

    /**
     * removes given album from the database
     *
     * @param album the album
     */
    public void removeAlbum(final Album album) {
        getAlbums().remove(album);
    }

    /**
     * removes given podcast from the database
     *
     * @param podcast the podcast
     */
    public void removePodcast(final Podcast podcast) {
        getPodcasts().remove(podcast);
    }

    /**
     * gets all the artists from the database
     *
     * @return the artists
     */
    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();

        for (User user : getUsers()) {
            if (user.getUserType() == UserType.ARTIST) {
                artists.add((Artist) user);
            }
        }

        return artists;
    }

    /**
     * gets owned playlists of the given user
     *
     * @param username the username
     * @return the owned playlists
     */
    public List<Playlist> getOwnedPlaylists(final String username) {
        List<Playlist> result = new ArrayList<>();

        for (Playlist playlist : getPlaylists()) {
            if (playlist.getOwner().equals(username)) {
                result.add(playlist);
            }
        }

        return result;
    }

    public void simulateTime(int timestamp) {
        for (User user : getUsers()) {
            if (user.getUserType() == UserType.LISTENER) {
                Listener listener = (Listener) user;

                if (listener.hasLoadedAFile()) {
                    listener.getPlayer().simulateTime(timestamp);
                }
            }
        }
    }
}
