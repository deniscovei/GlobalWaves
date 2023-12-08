package data.entities.user;

import commandManager.input.attributes.Stats;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audioPlayer.Player;
import data.entities.audio.audioFiles.Song;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;
import utils.Constants.Page;
import utils.Constants.UserType;

import java.util.ArrayList;

import static utils.Constants.NO_REPEAT;

@Getter
@Setter
public class Listener extends User {
    private boolean performedSearch = false;
    private ArrayList<File> searchResults = new ArrayList<>();
    private Player player = new Player();
    private File selectedFile = null;
    private ArrayList<Song> likedSongs = new ArrayList<>();
    private String previousCommand = null;
    private boolean online = true;
    private Stats stats = new Stats();

    public Listener(String username, int age, String city) {
        super(username, age, city);
        setUserType(UserType.LISTENER);
        setCurrentPage(Page.HOME_PAGE);
    }

    public Listener(UserInput user) {
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
        getLikedSongs().add(song);
    }

    public void unlike(final Song song) {
        getLikedSongs().remove(song);
    }

    public void deleteData() {
        setOnline(true);
        getPlayer().setRepeatState(NO_REPEAT);
        setPlayer(new Player());
        setSelectedFile(null);
        getPlayer().setLoadedFile(null);
        getLikedSongs().clear();
        getSearchResults().clear();
    }

    public File getLoadedFile() {
        return getPlayer().getLoadedFile();
    }

    public boolean havePerformedSearch() {
        return isPerformedSearch();
    }

    public void switchConnectionStatus(int timestamp) {
        setOnline(!isOnline());
        setStats(timestamp);
    }

    public void setStats(int timestamp) {
        if (hasLoadedAFile() && !getPlayer().hasFinished(timestamp)) {
            AudioFile currentPlayingFile = getPlayer().getCurrentPlayingFile(timestamp);
            getStats().setName(currentPlayingFile.getName());
            getStats().setPaused(getPlayer().isPaused());
            getStats().setRemainedTime(getPlayer().getRemainedTime(currentPlayingFile, timestamp));
            getStats().setRepeat(Constants.repeatStateToString(getPlayer().getRepeatState(),
                    getLoadedFile().getFileType()));
            getStats().setShuffle(getPlayer().isShuffleActivated());
        } else {
            getStats().setPaused(true);
        }
    }

    public ArrayList<Playlist> getFollowedPlaylists() {
        return Database.getInstance().getFollowedPlaylists(getUsername());
    }
}
