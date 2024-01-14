package data.entities.users;

import commandmanager.input.attributes.Stats;
import data.Database;
import data.entities.SearchBar;
import data.entities.Selection;
import data.entities.content.Merchandise;
import data.entities.files.File;
import data.entities.files.audioCollections.Playlist;
import data.entities.files.audioCollections.Podcast;
import data.entities.files.audioFiles.Ad;
import data.entities.files.audioFiles.AudioFile;
import data.entities.pages.*;
import data.entities.player.Playable;
import data.entities.player.Player;
import data.entities.files.audioFiles.Song;
import data.entities.users.contentCreator.Artist;
import data.entities.users.contentCreator.ContentCreator;
import data.entities.users.contentCreator.Host;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.UserType;
import utils.AppUtils.PageType;
import utils.AppUtils.FileType;
import utils.AppUtils.RecommendationType;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static utils.AppUtils.RES_COUNT_MAX;
import static utils.AppUtils.THIRTY;
import static utils.AppUtils.FIVE;
import static utils.AppUtils.THREE;
import static utils.AppUtils.TWO;
import static utils.AppUtils.sortMap;

/**
 * The type Listener.
 */
@Getter
@Setter
public final class Listener extends User {
    private boolean performedSearch = false;
    private SearchBar searchBar = new SearchBar();
    private Player player = new Player();
    private Selection selection = null;
    private List<Song> likedSongs = new ArrayList<>();
    private List<String> merches = new ArrayList<>();
    private String previousCommand = null;
    private boolean online = true;
    private Stats stats = new Stats();
    private Map<Song, Integer> listensPerSong = new HashMap<>();
    private List<File> history = new ArrayList<>();
    private List<ContentCreator> subscriptions = new ArrayList<>();
    private int premiumListens = 0;
    private int totalListens = 0;
    private Ad ad = null;
    private boolean deleted = false;
    private boolean premium = false;
    private int lastConnectedTimestamp = 0;
    private List<Song> songRecommendations = new ArrayList<>();
    private List<Playlist> playlistsRecommendations = new ArrayList<>();
    private File lastRecommendation = null;
    private int currentPageId = 0;
    private List<Page> pageHistory = new ArrayList<>();

    /**
     * The type Listener tops.
     */
    @Getter
    @Setter
    public final class ListenerTops implements User.Tops {
        private Map<String, Integer> topArtists = new HashMap<>();
        private Map<String, Integer> topGenres = new HashMap<>();
        private Map<String, Integer> topSongs = new HashMap<>();
        private Map<String, Integer> topAlbums = new HashMap<>();
        private Map<String, Integer> topEpisodes = new HashMap<>();

        /**
         * Instantiates a new Listener tops.
         */
        public ListenerTops() {

        }

        /**
         * Instantiates a new Listener tops.
         *
         * @param listenerTops the listener tops
         */
        public ListenerTops(final ListenerTops listenerTops) {
            this.topArtists = sortMap(listenerTops.getTopArtists(), RES_COUNT_MAX);
            this.topGenres = sortMap(listenerTops.getTopGenres(), RES_COUNT_MAX);
            this.topSongs = sortMap(listenerTops.getTopSongs(), RES_COUNT_MAX);
            this.topAlbums = sortMap(listenerTops.getTopAlbums(), RES_COUNT_MAX);
            this.topEpisodes = sortMap(listenerTops.getTopEpisodes(), RES_COUNT_MAX);
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
     * Select.
     *
     * @param file the file
     */
    public void select(final File file) {
        getPlayer().setListener(this);
        getPlayer().select(file);
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
     * @param timestamp               the timestamp
     * @param loadFromRecommendations the load from recommendations
     */
    public void loadAudioFile(final int timestamp, final boolean loadFromRecommendations) {
//        if (hasLoadedAFile())
//            System.out.println(getPlayer().getCurrentPlayingFile(timestamp).getName() + " at " +
//                "timestamp " + timestamp);
//        if (!getHistory().isEmpty() && hasLoadedAFile() && getPlayer().hasFinished(timestamp)
//            && getHistory().get(getHistory().size() - 1).getFileType() == FileType.AD) {
//            getHistory().remove(getHistory().size() - 1);
//        }

        getPlayer().setShuffleActivated(false);

        if (loadFromRecommendations) {
            select(getLastRecommendation());
            getPlayer().setLoadedFile(getLastRecommendation());
        } else {
            getPlayer().setLoadedFile(getSelection().getSelectedFile());
        }

        setSelection(null);

        getPlayer().play(timestamp);
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
     * Subscribe.
     *
     * @param contentCreator the content creator
     */
    public void subscribe(final ContentCreator contentCreator) {
        getSubscriptions().add(contentCreator);
    }

    /**
     * Unsubscribe.
     *
     * @param contentCreator the content creator
     */
    public void unsubscribe(final ContentCreator contentCreator) {
        getSubscriptions().remove(contentCreator);
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
     * @param pageType  the page type
     * @param timestamp the timestamp
     */
    public void changePage(final PageType pageType, final int timestamp) {
        int lastId = getPageHistory().size() - 1;

        while (lastId > getCurrentPageId()) {
            getPageHistory().remove(lastId);
            lastId--;
        }

        switch (pageType) {
            case HOME_PAGE:
                setCurrentPage(new HomePage(this));
                break;
            case LIKED_CONTENT_PAGE:
                setCurrentPage(new LikedContentPage(this));
                break;
            case HOST_PAGE:
                Podcast podcast = (Podcast) getPlayer().getLoadedFile();
                Host host = (Host) Database.getInstance().findUser(podcast.getOwner());
                setCurrentPage(new HostPage(host));
                break;
            case ARTIST_PAGE:
                Song song = (Song) getPlayer().getCurrentPlayingFile(timestamp);
                Artist artist = (Artist) Database.getInstance().findUser(song.getArtist());
                setCurrentPage(new ArtistPage(artist));
                break;
            default:
                break;
        }

        getPageHistory().add(getCurrentPage());
        setCurrentPageId(getPageHistory().size() - 1);
    }

    /**
     * Go to next page boolean.
     *
     * @return the boolean
     */
    public boolean goToNextPage() {
        if (getCurrentPageId() == getPageHistory().size() - 1) {
            return false;
        }

        setCurrentPageId(getCurrentPageId() + 1);
        setCurrentPage(getPageHistory().get(getCurrentPageId()));
        return true;
    }

    /**
     * Go to previous page boolean.
     *
     * @return the boolean
     */
    public boolean goToPreviousPage() {
        if (getCurrentPageId() == 0) {
            return false;
        }

        setCurrentPageId(getCurrentPageId() - 1);
        setCurrentPage(getPageHistory().get(getCurrentPageId()));
        return true;
    }

    /**
     * Buy merch.
     *
     * @param artist the artist
     * @param merch  the merch
     */
    public void buyMerch(final Artist artist, final Merchandise merch) {
        getMerches().add(merch.getName());
        artist.setMerchRevenue(artist.getMerchRevenue() + merch.getPrice());
    }

    /**
     * Push ad.
     *
     * @param ad the ad
     */
    public void pushAd(final Ad ad) {
        setAd(ad);
    }

    /**
     * Pop ad ad.
     *
     * @return the ad
     */
    public Ad popAd() {
        Ad ad = getAd();
        setAd(null);
        return ad;
    }

    /**
     * Has pushed ad boolean.
     *
     * @return the boolean
     */
    public boolean hasPushedAd() {
        return getAd() != null;
    }

    /**
     * Buy premium subscription.
     */
    public void buyPremiumSubscription() {
        setPremium(true);
    }

    /**
     * Cancel premium subscription.
     */
    public void cancelPremiumSubscription() {
        setPremium(false);
    }

    /**
     * Update recommendations boolean.
     *
     * @param recommendationType the recommendation type
     * @param timestamp          the timestamp
     * @return the boolean
     */
    public boolean updateRecommendations(final RecommendationType recommendationType,
                                         final int timestamp) {
        return switch (recommendationType) {
            case RANDOM_SONG -> updateRandomSongRecommendation(timestamp);
            case RANDOM_PLAYLIST -> updateRandomPlaylistRecommendation(timestamp);
            case FANS_PLAYLIST -> updateFansPlaylistRecommendation(timestamp);
        };
    }

    private boolean updateRandomSongRecommendation(final int timestamp) {
        if (!hasLoadedAFile() || getPlayer().hasFinished(timestamp)
            || getPlayer().getCurrTimeOfFile(timestamp) < THIRTY) {
            return false;
        }

        List<Song> songs = Database.getInstance().getSongs();
        List<Song> recommendedSongs = new ArrayList<>();
        String genre = ((Song) getPlayer().getCurrentPlayingFile(timestamp)).getGenre();

        for (Song song : songs) {
            if (song.getGenre().equals(genre)) {
                recommendedSongs.add(song);
            }
        }

        if (recommendedSongs.isEmpty()) {
            return false;
        }

        Random random = new Random(getPlayer().getCurrTimeOfFile(timestamp));
        int index = random.nextInt(recommendedSongs.size());
        getSongRecommendations().add(recommendedSongs.get(index));
        setLastRecommendation(recommendedSongs.get(index));

        return true;
    }

    private boolean updateRandomPlaylistRecommendation(final int timestamp) {
        Map<String, Integer> topGenres = new HashMap<>();

        for (Song song : getLikedSongs()) {
            topGenres.put(song.getGenre(), topGenres.getOrDefault(song.getGenre(), 0) + 1);
        }

        List<Playlist> playlists = Database.getInstance().getPlaylists();

        for (Playlist playlist : playlists) {
            if (!playlist.getOwner().equals(getUsername())
                && !playlist.getFollowerNames().contains(getUsername())) {
                continue;
            }

            for (AudioFile audioFile : playlist.getAudioFiles()) {
                Song song = (Song) audioFile;
                topGenres.put(song.getGenre(), topGenres.getOrDefault(song.getGenre(), 0) + 1);
            }
        }

        sortMap(topGenres, THREE);

        Playlist playlist = new Playlist(getUsername() + "'s recommendations",
            getUsername(), timestamp);

        int count = 0;
        int[] resCount = {FIVE, THREE, TWO};

        for (String genre : topGenres.keySet()) {
            List<Song> songs = Database.getInstance().getSongs();
            List<Song> genreSongs = new ArrayList<>();

            for (Song song : songs) {
                if (song.getGenre().equals(genre)) {
                    genreSongs.add(song);
                }
            }

            playlist.addAllSongs(genreSongs.stream()
                .sorted(Comparator
                    .<Song, Integer>comparing(song -> song.getUsersWhoLiked().size())
                    .reversed())
                .limit(resCount[count++])
                .toList());
        }

        if (playlist.getAudioFiles().isEmpty()) {
            return false;
        }

        getPlaylistsRecommendations().add(playlist);
        setLastRecommendation(playlist);
        Database.getInstance().addPlaylist(playlist);

        return true;
    }

    private boolean updateFansPlaylistRecommendation(final int timestamp) {
        String artistName = ((Song) getPlayer().getCurrentPlayingFile(timestamp)).getArtist();
        Artist artist = (Artist) Database.getInstance().findUser(artistName);
        Artist.ArtistTops artistTops = (Artist.ArtistTops) artist.getTops().clone();
        List<String> topFans = artistTops.getTopFans();

        Playlist playlist = new Playlist(artistName + " Fan Club recommendations",
            getUsername(), timestamp);

        for (String fanName : topFans) {
            Listener listener = (Listener) Database.getInstance().findUser(fanName);

            if (listener.getLikedSongs().isEmpty()) {
                continue;
            }

            playlist.addAllSongs(listener.getLikedSongs().stream()
                .sorted(Comparator
                    .<Song, Integer>comparing(song -> song.getUsersWhoLiked().size())
                    .reversed())
                .limit(RES_COUNT_MAX)
                .toList());
        }

        if (playlist.getAudioFiles().isEmpty()) {
            return false;
        }

        getPlaylistsRecommendations().add(playlist);
        setLastRecommendation(playlist);
        Database.getInstance().addPlaylist(playlist);

        return true;
    }
}
