package data.entities.user;

import data.entities.audio.File;
import data.entities.audio.audioFiles.Song;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class User {
    private String username = null;
    private int age = 0;
    private String city = null;
    private ArrayList<File> searchResults = new ArrayList<>();
    //private Player player = new Player();
    private File selection = null;
    boolean loaded = false;
    private final ArrayList<Song> prefferedSongs = new ArrayList<>();

    public User() {
    }

    public User(UserInput user) {
        this.username = user.getUsername();
        this.age = user.getAge();
        this.city = user.getCity();
    }

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    public User(final String username, final int age, final String city,
                final ArrayList<File> searchResults) {
        this(username, age, city);
        this.searchResults = searchResults;
    }

    public void loadAudioFile() {
        setLoaded(true);
    }

    public void unloadAudioFile() {
        setLoaded(false);
    }

    public boolean hasLoadedAudioFile() {
        return loaded;
    }

    public void like(final Song song) {
        prefferedSongs.add(song);
    }

    public void unlike(final Song song) {
        prefferedSongs.remove(song);
    }
}
