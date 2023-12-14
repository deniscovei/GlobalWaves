package data.entities.users;

import data.Database;
import data.entities.audio.audioCollections.Podcast;
import data.entities.content.Announcement;
import data.entities.pages.HostPage;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.FileType;
import utils.AppUtils.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Host extends User {
    private List<Podcast> podcasts = new ArrayList<>();
    private List<Announcement> announcements = new ArrayList<>();

    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        setUserType(UserType.HOST);
        setCurrentPage(new HostPage(this));
    }

    /**
     * adds an announcement to the host's list of announcements
     */
    public void addAnnouncement(final Announcement announcement) {
        announcements.add(announcement);
    }

    /**
     * finds an announcement in the host's list of announcements
     */
    public Announcement findAnnouncement(final String name) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return announcement;
            }
        }
        return null;
    }

    /**
     * finds a podcast in the host's list of podcasts
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
     */
    public void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
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
     */
    public void removeAnnouncement(final Announcement announcement) {
        getAnnouncements().remove(announcement);
    }

    /**
     * removes a podcast from the host's list of podcasts
     */
    public void removePodcast(final Podcast podcast) {
        getPodcasts().remove(podcast);
    }
}
