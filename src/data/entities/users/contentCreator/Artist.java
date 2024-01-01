package data.entities.users.contentCreator;

import data.Database;
import data.entities.files.audioCollections.Album;
import data.entities.files.audioFiles.AudioFile;
import data.entities.files.audioFiles.Song;
import data.entities.content.Event;
import data.entities.content.Merchandise;
import data.entities.pages.ArtistPage;
import data.entities.users.Listener;
import data.entities.users.User;
import data.entities.users.contentCreator.ContentCreator;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.UserType;
import utils.AppUtils.FileType;

import java.util.*;
import java.util.stream.Collectors;

import static utils.AppUtils.RES_COUNT_MAX;

/**
 * The type Artist.
 */
@Getter
@Setter
public class Artist extends ContentCreator {
    private List<Album> albums = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private List<Merchandise> merchandise = new ArrayList<>();
    private Map<Listener, Integer> listens = new HashMap<>();
    private Map<Song, Double> revenuePerSong = new HashMap<>();
    private double songRevenue = 0.0;
    private double merchRevenue = 0.0;

    @Getter
    @Setter
    public class ArtistTops implements Tops {
        private Map<String, Integer> topAlbums = new HashMap<>();
        private Map<String, Integer> topSongs = new HashMap<>();
        private List<String> topFans = new ArrayList<>();
        private int listeners = 0;

        public ArtistTops() {

        }

        public ArtistTops(ArtistTops artistTops) {
            this.topAlbums = sortMap(artistTops.getTopAlbums());
            this.topSongs = sortMap(artistTops.getTopSongs());
            //this.topFans = new ArrayList<>(artistTops.getTopFans());
            //this.topFans = sortList(artistTops.getTopFans());
            this.topFans = sortList(artistTops.getTopFans()).stream()
                    .limit(RES_COUNT_MAX)
                    .collect(Collectors.toList());
            this.listeners = artistTops.getListeners();
        }

        List<String> sortList(List<String> topFans) {
            topFans.sort(Comparator.<String, Integer>comparing(
                            fanName -> ((Listener.ListenerTops) Database.getInstance().findUser(fanName).getTops())
                                    .getTopArtists().get(getUsername())).reversed()
                    .thenComparing(Comparator.naturalOrder()));

            return topFans;
        }

        @Override
        public Tops clone() {
            return new ArtistTops(this);
        }
    }

    public Artist(final String username) {
        this.username = username;
        tops = new ArtistTops();
        setUserType(UserType.ARTIST);
        setCurrentPage(new ArtistPage(this));
    }

    /**
     * Instantiates a new Artist.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        tops = new ArtistTops();
        setUserType(UserType.ARTIST);
        setCurrentPage(new ArtistPage(this));
    }

    /**
     * finds an album in the artist's list of albums
     *
     * @param name the name
     * @return the album
     */
    public Album findAlbum(final String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    /**
     * adds an album to the artist's list of albums
     *
     * @param album the album
     */
    public void addAlbum(final Album album) {
        getAlbums().add(album);
    }

    /**
     * finds an event in the artist's list of events
     *
     * @param name the name
     * @return the event
     */
    public Event findEvent(final String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    /**
     * adds an event to the artist's list of events
     *
     * @param event the event
     */
    public void addEvent(final Event event) {
        getEvents().add(event);
    }

    /**
     * finds a merchandise in the artist's list of merchandise
     *
     * @param name the name
     * @return the merchandise
     */
    public Merchandise findMerch(final String name) {
        for (Merchandise merch : getMerchandise()) {
            if (merch.getName().equals(name)) {
                return merch;
            }
        }
        return null;
    }

    /**
     * adds a merchandise to the artist's list of merchandise
     *
     * @param newMerchandise the new merchandise
     */
    public void addMerchandise(final Merchandise newMerchandise) {
        getMerchandise().add(newMerchandise);
    }

    /**
     * checks if the artist is interacting with others
     */
    @Override
    public boolean interactingWithOthers(final int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == UserType.LISTENER) {
                Listener listener = (Listener) user;

                if (listener.getCurrentPage().getCreator().equals(this)) {
                    return true;
                }

                if (!Objects.requireNonNull(listener).hasLoadedAFile()
                    || listener.getPlayer().hasFinished(timestamp)) {
                    continue;
                }

                AudioFile currentPlayingFile =
                    listener.getPlayer().getCurrentPlayingFile(timestamp);
                if (currentPlayingFile.getFileType().equals(FileType.SONG)
                    && ((Song) currentPlayingFile).getArtist().equals(getUsername())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * removes an album from the artist's albums
     *
     * @param album the album
     */
    public void removeAlbum(final Album album) {
        getAlbums().remove(album);
        Database.getInstance().removeAlbum(album);
    }

    /**
     * removes an event from the artist's events
     *
     * @param event the event
     */
    public void removeEvent(final Event event) {
        getEvents().remove(event);
    }

    /**
     * gets the number of likes of the artist's albums
     *
     * @return the number of likes
     */
    public int getNumberOfLikes() {
        int numberOfLikes = 0;
        for (Album album : getAlbums()) {
            numberOfLikes += album.getNumberOfLikes();
        }

        return numberOfLikes;
    }
}
