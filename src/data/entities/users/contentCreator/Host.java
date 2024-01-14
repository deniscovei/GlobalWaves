package data.entities.users.contentCreator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import data.Database;
import data.entities.Notification;
import data.entities.files.audioCollections.Podcast;
import data.entities.content.Announcement;
import data.entities.pages.HostPage;
import data.entities.users.Listener;
import data.entities.users.User;
import data.entities.users.contentCreator.ContentCreator;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.FileType;
import utils.AppUtils.UserType;

import java.util.*;

import static utils.AppUtils.RES_COUNT_MAX;
import static utils.AppUtils.sortMap;

/**
 * The type Host.
 */
@Getter
@Setter
public class Host extends ContentCreator {
    private List<Podcast> podcasts = new ArrayList<>();
    private List<Announcement> announcements = new ArrayList<>();

    @Getter
    @Setter
    public final class HostTops implements Tops {
        private Map<String, Integer> topEpisodes = new HashMap<>();
        @JsonIgnore
        private Set<String> topFans = new HashSet<>();

        public HostTops() {

        }

        public HostTops(HostTops hostTops) {
            this.topEpisodes = sortMap(hostTops.getTopEpisodes(), RES_COUNT_MAX);
            this.topFans = hostTops.getTopFans();
        }

        public int getListeners() {
            return getTopFans().size();
        }

        @Override
        public Tops clone() {
            return new HostTops(this);
        }
    }

    /**
     * Instantiates a new Host.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        this.tops = new HostTops();
        setUserType(UserType.HOST);
        setCurrentPage(new HostPage(this));
    }

    public Host(String hostName) {
        this.username = hostName;
        this.tops = new HostTops();
        setUserType(UserType.HOST);
        setCurrentPage(new HostPage(this));
    }

    /**
     * adds an announcement to the host's list of announcements
     *
     * @param announcement the announcement
     */
    public void addAnnouncement(final Announcement announcement) {
        getAnnouncements().add(announcement);
        for (Listener listener : getSubscribers()) {
            listener.getNotifications().add(new Notification("New Announcement",
                "New Announcement from " + getUsername() + "."));
        }
    }

    /**
     * finds an announcement in the host's list of announcements
     *
     * @param name the name
     * @return the announcement
     */
    public Announcement findAnnouncement(final String name) {
        for (Announcement announcement : getAnnouncements()) {
            if (announcement.getName().equals(name)) {
                return announcement;
            }
        }
        return null;
    }

    /**
     * finds a podcast in the host's list of podcasts
     *
     * @param name the name
     * @return the podcast
     */
    public Podcast findPodcast(final String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }

    /**
     * adds a podcast to the host's list of podcasts
     *
     * @param podcast the podcast
     */
    public void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
        for (Listener listener : getSubscribers()) {
            listener.getNotifications().add(new Notification("New Podcast",
                "New Podcast from " + getUsername() + "."));
        }
    }

    /**
     * checks if the host is interacting with others
     */
    @Override
    public boolean interactingWithOthers(final int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == UserType.LISTENER) {
                Listener listener = (Listener) user;

                if (listener.getCurrentPage().getCreator().equals(this)) {
                    return true;
                }

                if (!Objects.requireNonNull(listener).hasLoadedAFile()) {
                    continue;
                }

                if (Objects.requireNonNull(listener.getPlayer().getLoadedFile()).getFileType()
                    == FileType.PODCAST
                    && ((Podcast) listener.getPlayer().getLoadedFile()).getOwner()
                    .equals(getUsername())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * removes an announcement from the host's list of announcements
     *
     * @param announcement the announcement
     */
    public void removeAnnouncement(final Announcement announcement) {
        getAnnouncements().remove(announcement);
    }

    /**
     * removes a podcast from the host's list of podcasts
     *
     * @param podcast the podcast
     */
    public void removePodcast(final Podcast podcast) {
        getPodcasts().remove(podcast);
        Database.getInstance().removePodcast(podcast);
    }
}
