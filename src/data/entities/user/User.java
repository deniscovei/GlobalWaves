package data.entities.user;

import data.entities.audio.File;
import data.entities.audio.audioCollections.Playlist;
import data.entities.user.audioPlayer.Player;
import data.entities.audio.audioFiles.Song;
import fileio.input.EpisodeInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;

@Setter
@Getter
public class User {
    private String username = null;
    private int age = 0;
    private String city = null;
    private ArrayList<File> searchResults = new ArrayList<>();
    private Player player = new Player();
    private File selectedFile = null;
    private ArrayList<Song> prefferedSongs = new ArrayList<>();
    private String previousCommand = null;

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

    public File getSelection() {
        return player.getSelection();
    }

    public void select(File selection) {
        setSelectedFile(selection);
        player.select(selection);
    }

    public void loadAudioFile(int timestamp) {
        player.setLoadedFile(selectedFile);
        player.play(timestamp);
    }

    public void unloadAudioFile(int timestamp) {
        player.pause(timestamp);
        player.removeLoadedSongs();
        player.removeLoadedPlaylists();
        player.setLoadedFile(null);
        player.setRepeatState(Constants.NO_REPEAT);
        setSelectedFile(null);
    }

    public boolean hasLoadedAFile() {
        return player.getLoadedFile() != null;
    }

    public void like(final Song song) {
        prefferedSongs.add(song);
    }

    public void unlike(final Song song) {
        prefferedSongs.remove(song);
    }

    public void deleteData() {
        getPlayer().setRepeatState(Constants.NO_REPEAT);
        setPlayer(new Player());
        player.setLoadedFile(null);
        getPrefferedSongs().clear();
        getSearchResults().clear();
    }

    public File getLoadedFile() {
        return player.getLoadedFile();
    }
}
