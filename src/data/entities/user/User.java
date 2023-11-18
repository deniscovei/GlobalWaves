package data.entities.user;

import data.entities.audio.File;
import data.entities.user.audioPlayer.Player;
import data.entities.audio.audioFiles.Song;
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

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    public User(UserInput user) {
        this(user.getUsername(), user.getAge(), user.getCity());
    }

    public void select(File selection) {
        setSelectedFile(selection);
        player.select(selection);
    }

    public void loadAudioFile(int timestamp) {
        player.setLoadedFile(selectedFile);
        setSelectedFile(null);
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
        setSelectedFile(null);
        player.setLoadedFile(null);
        getPrefferedSongs().clear();
        getSearchResults().clear();
    }

    public File getLoadedFile() {
        return player.getLoadedFile();
    }
}
