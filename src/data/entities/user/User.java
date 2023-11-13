package data.entities.user;

import data.entities.audio.base.AudioFile;
import fileio.input.UserInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class User {
    private String username = null;
    private int age = 0;
    private String city = null;
    private final ArrayList<AudioFile> searchResults = new ArrayList<>();
    private AudioFile selection = null;
    boolean loaded = false;

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
                final ArrayList<AudioFile> searchResults) {
        this(username, age, city);
        this.searchResults.addAll(searchResults);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSearchResults(ArrayList<AudioFile> searchResults) {
        this.searchResults.addAll(searchResults);
    }

    public void setSelection(AudioFile selection) {
        this.selection = selection;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void loadAudioFile() {
        setLoaded(true);
    }

    public boolean hasLoadedAudioFile() {
        return loaded;
    }
}
