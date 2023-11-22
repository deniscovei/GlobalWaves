package data.entities.user;

import data.entities.audio.File;
import data.entities.user.audioplayer.Player;
import data.entities.audio.audiofiles.Song;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static utils.Constants.NO_REPEAT;

@Setter
@Getter
public final class User {
    private String username = null;
    private int age = 0;
    private String city = null;
    private boolean performedSearch = false;
    private ArrayList<File> searchResults = new ArrayList<>();
    private Player player = new Player();
    private File selectedFile = null;
    private ArrayList<Song> preferredSongs = new ArrayList<>();
    private String previousCommand = null;

    public User() {
    }

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    public User(final UserInput user) {
        this(user.getUsername(), user.getAge(), user.getCity());
    }

    public void select(final File selection) {
        setSelectedFile(selection);
        getPlayer().select(selection);
        setPerformedSearch(false);
    }

    public void loadAudioFile(final int timestamp) {
        getPlayer().setLoadedFile(getSelectedFile());
        getPlayer().play(timestamp);
        setSelectedFile(null);
    }

    public void unloadAudioFile(final int timestamp) {
        getPlayer().pause(timestamp);
        getPlayer().removeLoadedSongs();
        getPlayer().removeLoadedPlaylists();
        getPlayer().setLoadedFile(null);
        getPlayer().setRepeatState(NO_REPEAT);
        setSelectedFile(null);
    }

    public boolean hasLoadedAFile() {
        return getPlayer().getLoadedFile() != null;
    }

    public void like(final Song song) {
        getPreferredSongs().add(song);
    }

    public void unlike(final Song song) {
        getPreferredSongs().remove(song);
    }

    public void deleteData() {
        getPlayer().setRepeatState(NO_REPEAT);
        setPlayer(new Player());
        setSelectedFile(null);
        getPlayer().setLoadedFile(null);
        getPreferredSongs().clear();
        getSearchResults().clear();
    }

    public File getLoadedFile() {
        return getPlayer().getLoadedFile();
    }

    public boolean havePerformedSearch() {
        return isPerformedSearch();
    }
}
