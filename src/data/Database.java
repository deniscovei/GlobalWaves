package data;

import data.entities.audio.audiocollections.Playlist;
import data.entities.audio.audiocollections.Podcast;
import data.entities.audio.audiofiles.Song;
import data.entities.user.User;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public final class Database {
    private static Database instance;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Song> songs = new ArrayList<>();
    private final ArrayList<Podcast> podcasts = new ArrayList<>();
    private final ArrayList<Playlist> playlists = new ArrayList<>();

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
            addUser(new User(user));
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
}
