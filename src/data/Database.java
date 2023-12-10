package data;

import data.entities.audio.audioCollections.Album;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioCollections.Podcast;
import data.entities.audio.audioFiles.Song;
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
import utils.Extras.UserType;

import java.util.ArrayList;

@Setter
@Getter
public final class Database {
    private static Database instance;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Song> songs = new ArrayList<>();
    private final ArrayList<Podcast> podcasts = new ArrayList<>();
    private final ArrayList<Playlist> playlists = new ArrayList<>();
    private final ArrayList<Album> albums = new ArrayList<>();

    private Database() {
    }

    /**
     * returns the database instance
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * returns true if the database has been instantiated
     */
    public static boolean instantiated() {
        return instance != null;
    }

    /**
     * returns the user with the given username
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
     */
    public void addUser(final User user) {
        getUsers().add(user);
    }

    /**
     * adds a song to the database
     */
    public void addSong(final Song song) {
        getSongs().add(song);
    }

    /**
     * adds a podcast to the database
     */
    public void addPodcast(final Podcast podcast) {
        getPodcasts().add(podcast);
    }

    /**
     * adds a playlist to the database
     */
    public void addPlaylist(final Playlist playlist) {
        getPlaylists().add(playlist);
    }

    /**
     * uploads the library
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
     * removes all the playlists from the database
     */
    public void removePlaylists() {
        getPlaylists().clear();
    }

    /**
     * removes all the songs' likes from the database
     */
    public void removeLikes() {
        for (Song song : getSongs()) {
            song.getUsersWhoLiked().clear();
        }
    }

    public void addUser(final String username, final int age, final String city, final UserType userType) {
        User user;
        switch (userType) {
            case ARTIST:
                user = new Artist(username, age, city);
                break;
            case HOST:
                user = new Host(username, age, city);
                break;
            case LISTENER:
            default:
                user = new Listener(username, age, city);
                break;
        }
        user.setAdded(true);
        addUser(user);
    }

    public void flush() {
        // delete the data of the users
        for (User user : getUsers()) {
            if (user.getUserType().equals(UserType.LISTENER)) {
                ((Listener) user).deleteData();
            }
        }
        // delete previously created albums
        removeAlbums();
        // delete previously created playlists
        removePlaylists();
        // delete liked songs
        removeLikes();
        // delete added users
        getUsers().removeIf(User::isAdded);
        // delete added songs
        getSongs().removeIf(Song::isAdded);
        // delete added podcasts
        getPodcasts().removeIf(Podcast::isAdded);
    }

    private void removeAlbums() {
        getAlbums().clear();
    }

    public ArrayList<Playlist> getFollowedPlaylists(final String username) {
        ArrayList<Playlist> result = new ArrayList<>();

        for (Playlist playlist : Database.getInstance().getPlaylists()) {
            if (playlist.getOwner().equals(username)) {
                result.add(playlist);
            }
        }

        return result;
    }

    public Song findSong(final String songName) {
        for (Song song : getSongs()) {
            if (song.getName().equals(songName)) {
                return song;
            }
        }
        return null;

    }

    public Podcast findPodcast(final String podcastName) {
        for (Podcast podcast : getPodcasts()) {
            if (podcast.getName().equals(podcastName)) {
                return podcast;
            }
        }
        return null;
    }

    public Album findAlbum(final String albumName) {
        for (Album album : getAlbums()) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }

    public void removeUser(User user) {
        switch (user.getUserType()) {
            case LISTENER:
                if (user.isAdded()) {
                    getUsers().remove(user);
                } else {
                    ((Listener) user).setDeleted(true);
                }
                break;
            case ARTIST:
                getUsers().remove(user);
                removeSongs(user);
                break;
            case HOST:
                getUsers().remove(user);
                break;
        }
    }

    private void removeSongs(User artist) {
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
                listener.getLikedSongs().removeIf(song -> song.getArtist().equals(artist.getUsername()));
            }
        }
    }

    public void removeAlbum(Album album) {
        getAlbums().remove(album);
    }

    public ArrayList<Artist> getArtists() {
        ArrayList<Artist> artists = new ArrayList<>();

        for (User user : getUsers()) {
            if (user.getUserType() == UserType.ARTIST) {
                artists.add((Artist) user);
            }
        }

        return artists;
    }

    public ArrayList<Podcast> findPodcasts(final String username) {
        ArrayList<Podcast> result = new ArrayList<>();

        for (Podcast podcast : getPodcasts()) {
            if (podcast.getOwner().equals(username)) {
                result.add(podcast);
            }
        }

        return result;
    }

    public ArrayList<Album> findAlbums(String username) {
        ArrayList<Album> result = new ArrayList<>();

        for (Album album : getAlbums()) {
            if (album.getOwner().equals(username)) {
                result.add(album);
            }
        }

        return result;
    }
}
