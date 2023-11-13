package data.database;

import data.entities.audio.derived.Playlist;
import data.entities.audio.derived.Podcast;
import data.entities.audio.derived.Song;
import data.entities.user.User;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;

import java.util.ArrayList;

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

    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void setUsers(final ArrayList <User> users) {
        this.users.addAll(users);
    }

    public void setSongs(final ArrayList <Song> songs) {
        this.songs.addAll(songs);
    }

    public void setPodcasts(final ArrayList <Podcast> podcasts) {
        this.podcasts.addAll(podcasts);
    }

    public void setPlaylists(final ArrayList <Playlist> playlists) {
        this.playlists.addAll(playlists);
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
}
