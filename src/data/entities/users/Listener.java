package data.entities.users;

import commandmanager.input.attributes.Stats;
import data.Database;
import data.entities.SearchBar;
import data.entities.Selection;
import data.entities.files.File;
import data.entities.files.audioCollections.Playlist;
import data.entities.files.audioFiles.AudioFile;
import data.entities.player.Playable;
import data.entities.player.Player;
import data.entities.files.audioFiles.Song;
import data.entities.pages.HomePage;
import data.entities.pages.LikedContentPage;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.UserType;
import utils.AppUtils.PageType;
import utils.AppUtils.FileType;

import java.util.*;

/**
 * The type Listener.
 */
@Getter
@Setter
public class Listener extends User {
    private boolean performedSearch = false;
    private SearchBar searchBar = new SearchBar();
    private Player player = new Player();
    private Selection selection = null;
    private List<Song> likedSongs = new ArrayList<>();
    private String previousCommand = null;
    private boolean online = true;
    private Stats stats = new Stats();
    private boolean deleted = false;
    private int lastConnectedTimestamp = 0;

    @Getter
    @Setter
    public class ListenerTops implements User.Tops {
        private Map<String, Integer> topArtists = new HashMap<>();
        private Map<String, Integer> topGenres = new HashMap<>();
        private Map<String, Integer> topSongs = new HashMap<>();
        private Map<String, Integer> topAlbums = new HashMap<>();
        private Map<String, Integer> topEpisodes = new HashMap<>();

        public ListenerTops() {

        }

        public ListenerTops(ListenerTops listenerTops) {
            this.topArtists = sortMap(listenerTops.getTopArtists());
            this.topGenres = sortMap(listenerTops.getTopGenres());
            this.topSongs = sortMap(listenerTops.getTopSongs());
            this.topAlbums = sortMap(listenerTops.getTopAlbums());
            this.topEpisodes = sortMap(listenerTops.getTopEpisodes());
        }

        @Override
        public Tops clone() {
            return new ListenerTops(this);
        }
    }

    /**
     * Instantiates a new Listener.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Listener(final String username, final int age, final String city) {
        super(username, age, city);
        tops = new ListenerTops();
        setUserType(UserType.LISTENER);
        setCurrentPage(new HomePage(this));
    }

    /**
     * Instantiates a new Listener.
     *
     * @param user the user
     */
    public Listener(final UserInput user) {
        this(user.getUsername(), user.getAge(), user.getCity());
    }

    /**
     * selects a file or a page
     *
     * @param newSelection the new selection
     */
    public void select(final Selection newSelection) {
        getPlayer().setListener(this);
        if (newSelection.getSelectionType() == AppUtils.SelectionType.FILE) {
            getPlayer().select(newSelection.getSelectedFile());
        } else {
            setCurrentPage(newSelection.getSelectedPage());
        }

        setSelection(newSelection);
        setPerformedSearch(false);
    }

    /**
     * gets the selected file
     *
     * @return the selected file
     */
    public File getSelectedFile() {
        if (getSelection() == null) {
            return null;
        }
        return getSelection().getSelectedFile();
    }

    /**
     * loads an audio file
     *
     * @param timestamp the timestamp
     */
    public void loadAudioFile(final int timestamp) {
        getPlayer().setShuffleActivated(false);
        getPlayer().setLoadedFile(getSelection().getSelectedFile());
        getPlayer().play(timestamp);
        setSelection(null);
    }

    /**
     * unloads the loaded file
     *
     * @param timestamp the timestamp
     */
    public void unloadAudioFile(final int timestamp) {
        getPlayer().pause(timestamp);
        getPlayer().removeLoadedSongs();
        getPlayer().removeLoadedPlaylists();
        getPlayer().removeLoadedAlbums();
        getPlayer().setLoadedFile(null);
        setSelection(null);
    }

    /**
     * checks if the user has loaded a file
     *
     * @return the boolean
     */
    public boolean hasLoadedAFile() {
        return getPlayer().getLoadedFile() != null;
    }

    /**
     * likes a song
     *
     * @param song the song
     */
    public void like(final Song song) {
        getLikedSongs().add(song);
    }

    /**
     * unlikes a song
     *
     * @param song the song
     */
    public void unlike(final Song song) {
        getLikedSongs().remove(song);
    }

    /**
     * deletes the user's data
     */
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

    /**
     * gets the loaded file
     *
     * @return the loaded file
     */
    public File getLoadedFile() {
        return getPlayer().getLoadedFile();
    }

    /**
     * checks if the user has performed a search
     *
     * @return the boolean
     */
    public boolean havePerformedSearch() {
        return performedSearch;
    }

    /**
     * switches the connection status
     *
     * @param timestamp the timestamp
     */
    public void switchConnectionStatus(final int timestamp) {
        if (isOnline()) {
            setLastConnectedTimestamp(timestamp);
        } else if (getPlayer().getCurrentPlayerFileIndex() != -1) {
            Playable currentPlayingFile =
                    getPlayer().getPlayerFiles().get(getPlayer().getCurrentPlayerFileIndex());

            currentPlayingFile.setOffset(currentPlayingFile.getOffset() - timestamp
                    + getLastConnectedTimestamp());
        }

        setOnline(!isOnline());
        setStats(timestamp);
    }

    /**
     * sets the stats
     *
     * @param timestamp the timestamp
     */
    public void setStats(final int timestamp) {
        if (hasLoadedAFile() && !getPlayer().hasFinished(timestamp)) {
            AudioFile currentPlayingFile = getPlayer().getCurrentPlayingFile(timestamp);
            getStats().setName(currentPlayingFile.getName());
            getStats().setPaused(getPlayer().isPaused());
            getStats().setRemainedTime(getPlayer().getRemainedTime(currentPlayingFile, timestamp));
            getStats().setRepeat(AppUtils.repeatStateToString(getPlayer().getRepeatState(),
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

    /**
     * gets the followed playlists
     *
     * @return the followed playlists
     */
    public List<Playlist> getFollowedPlaylists() {
        return Database.getInstance().getFollowedPlaylists(getUsername());
    }

    /**
     * checks if the user is interacting with others
     *
     * @param timestamp the timestamp
     * @return true if the user is interacting with others and false otherwise
     */
    @Override
    public boolean interactingWithOthers(final int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user == this || user.getUserType() != UserType.LISTENER) {
                continue;
            }

            Listener listener = (Listener) user;

            if (listener.getCurrentPage().getCreator().equals(this)) {
                return true;
            }

            if (listener.hasLoadedAFile() && !listener.getPlayer().hasFinished(timestamp)) {
                File loadedFile = listener.getPlayer().getLoadedFile();

                if (loadedFile != null && loadedFile.getFileType() == FileType.PLAYLIST
                        && ((Playlist) loadedFile).getOwner().equals(getUsername())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * goes to the next page
     *
     * @param pageType the page type
     */
    public void goToNextPage(final PageType pageType) {
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
