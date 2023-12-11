package data.entities.users;

import commandManager.input.attributes.Stats;
import data.Database;
import data.entities.SearchBar;
import data.entities.Selection;
import data.entities.audio.File;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audioPlayer.Playable;
import data.entities.audioPlayer.Player;
import data.entities.audio.audioFiles.Song;
import data.entities.pages.HomePage;
import data.entities.pages.LikedContentPage;
import data.entities.pages.Page;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;
import utils.Extras;
import utils.Extras.UserType;
import utils.Extras.PageType;
import utils.Extras.FileType;

import java.util.ArrayList;
import java.util.Objects;

import static utils.Extras.NO_REPEAT;

@Getter
@Setter
public class Listener extends User {
    private boolean performedSearch = false;
    private SearchBar searchBar = new SearchBar();
    private Player player = new Player();
    private Selection selection = null;
    private ArrayList<Song> likedSongs = new ArrayList<>();
    private String previousCommand = null;
    private boolean online = true;
    private Stats stats = new Stats();
    private boolean deleted = false;
    private int lastConnectedTimestamp = 0;

    public Listener(String username, int age, String city) {
        super(username, age, city);
        setUserType(UserType.LISTENER);
        setCurrentPage(new HomePage(this));
    }

    public Listener(UserInput user) {
        this(user.getUsername(), user.getAge(), user.getCity());
    }

    public void select(final Selection selection) {
        switch (selection.getSelectionType()) {
            case FILE -> getPlayer().select(selection.getSelectedFile());
            case PAGE -> setCurrentPage(selection.getSelectedPage());
        }
        setSelection(selection);
        setPerformedSearch(false);
    }

    public File getSelectedFile() {
        if (getSelection() == null) {
            return null;
        }
        return getSelection().getSelectedFile();
    }

    public Page getSelectedPage() {
        return getSelection().getSelectedPage();
    }

    public void loadAudioFile(final int timestamp) {
        // here added
        getPlayer().setShuffleActivated(false);
        getPlayer().setLoadedFile(getSelection().getSelectedFile());
        getPlayer().play(timestamp);
        setSelection(null);
    }

    public void unloadAudioFile(final int timestamp) {
        getPlayer().pause(timestamp);
        getPlayer().removeLoadedSongs();
        getPlayer().removeLoadedPlaylists();
        getPlayer().removeLoadedAlbums();
        getPlayer().setLoadedFile(null);
        setSelection(null);
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
        setPlayer(new Player());
        setSelection(null);
        getPlayer().setLoadedFile(null);
        getLikedSongs().clear();
        getSearchBar().flush();
        setDeleted(false);
        setCurrentPage(new HomePage(this));
    }

    public File getLoadedFile() {
        return getPlayer().getLoadedFile();
    }

    public boolean havePerformedSearch() {
        return performedSearch;
    }

    public void switchConnectionStatus(int timestamp) {
        if (isOnline()) {
            setLastConnectedTimestamp(timestamp);
        } else if (getPlayer().getCurrentPlayerFileIndex() != -1) {
            Playable currentPlayingFile = getPlayer().getPlayerFiles().get(getPlayer().getCurrentPlayerFileIndex());
            currentPlayingFile.setOffset(currentPlayingFile.getOffset() - timestamp + getLastConnectedTimestamp());
        }
        setOnline(!isOnline());
        setStats(timestamp);
    }

    public void setStats(int timestamp) {
        if (hasLoadedAFile() && !getPlayer().hasFinished(timestamp)) {
            AudioFile currentPlayingFile = getPlayer().getCurrentPlayingFile(timestamp);
            getStats().setName(currentPlayingFile.getName());
            getStats().setPaused(getPlayer().isPaused());
            getStats().setRemainedTime(getPlayer().getRemainedTime(currentPlayingFile, timestamp));
            getStats().setRepeat(Extras.repeatStateToString(getPlayer().getRepeatState(),
                    getLoadedFile().getFileType()));
            getStats().setShuffle(getPlayer().isShuffleActivated());
        } else {
            getStats().setName("");
            getStats().setPaused(true);
            getStats().setRemainedTime(0);
            getStats().setRepeat("No Repeat");
            getStats().setShuffle(false);
        }
    }

    public ArrayList<Playlist> getFollowedPlaylists() {
        return Database.getInstance().getFollowedPlaylists(getUsername());
    }

    @Override
    public boolean interactingWithOthers(int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user == this) {
                continue;
            }

            if (user.getUserType() == UserType.LISTENER) {
                Listener listener = (Listener) user;

//                if (listener.getCurrentPage().getCreator().equals(this)) {
//                    return true;
//                }

                if (!Objects.requireNonNull(listener).hasLoadedAFile()
                        || listener.getPlayer().hasFinished(timestamp)) {
                    continue;
                }

                if (listener.getPlayer().getLoadedFile().getFileType() == FileType.PLAYLIST
                        && ((Playlist) listener.getPlayer().getLoadedFile()).getOwner().equals(getUsername())) {
                    return true;
                }

//                if (listener.getPlayer().getLoadedFile().getFileType() == Extras.FileType.PLAYLIST
//                        && ((Playlist) listener.getPlayer().getLoadedFile()).getOwner().equals(getUsername())) {
//                    return true;
//                }
            }
        }

        return false;
    }

    public void goToNextPage(PageType pageType) {
        switch (pageType) {
            case HOME_PAGE:
                setCurrentPage(new HomePage(this));
                break;
            case LIKED_CONTENT_PAGE:
                setCurrentPage(new LikedContentPage(this));
                break;
            default:
                break;
        }
    }
}
