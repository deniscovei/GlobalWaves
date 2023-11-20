package data;

import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioCollections.Podcast;
import data.entities.audio.audioFiles.Song;
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
public class Database {
    private static Database instance;
    private final ArrayList <User> users = new ArrayList<>();
    private final ArrayList <Song> songs = new ArrayList<>();
    private final ArrayList <Podcast> podcasts = new ArrayList<>();
    private final ArrayList <Playlist> playlists = new ArrayList<>();

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static boolean instantiated() {
        return instance != null;
    }

    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Playlist findPlaylist(String playlistName) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(playlistName)) {
                return playlist;
            }
        }
        return null;
    }

    public Playlist findPlaylist(int playlistId, String owner) {
        int currentId = 0;
        for (int id = 0; id < playlists.size(); id++) {
            Playlist playlist = playlists.get(id);
            if (playlist.getOwner().equals(owner)) {
                currentId++;
                if (currentId == playlistId) {
                    return playlist;
                }
            }
        }
        return null;
    }

    public void addUser(User user) {
        getUsers().add(user);
    }

    public void addSong(Song song) {
        getSongs().add(song);
    }

    public void addPodcast(Podcast podcast) {
        getPodcasts().add(podcast);
    }

    public void addPlaylist(Playlist playlist) {
        getPlaylists().add(playlist);
    }

    public void upload(LibraryInput library) {
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

    public void removePlaylists() {
        getPlaylists().clear();
    }

    public void removeLikedSongs() {
        for (Song song : getSongs()) {
            song.getUsersWhoLiked().clear();
        }
    }
}
