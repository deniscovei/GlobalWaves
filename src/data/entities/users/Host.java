package data.entities.users;

import data.Database;
import data.entities.audio.audioCollections.Podcast;
import data.entities.content.Announcement;
import data.entities.pages.HostPage;
import lombok.Getter;
import lombok.Setter;
import utils.Extras.FileType;
import utils.Extras.UserType;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class Host extends User {
    ArrayList<Podcast> podcasts = new ArrayList<>();
    ArrayList<Announcement> announcements = new ArrayList<>();

    public Host(String username, int age, String city) {
        super(username, age, city);
        setUserType(UserType.HOST);
        setCurrentPage(new HostPage(this));
    }

    public void addAnnouncement(Announcement announcement) {
        announcements.add(announcement);
    }

    public Announcement findAnnouncement(String name) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return announcement;
            }
        }
        return null;
    }

    public Podcast findPodcast(String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }

    public void addPodcast(Podcast podcast) {
        podcasts.add(podcast);
    }

    @Override
    public boolean interactingWithOthers(int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == UserType.LISTENER) {
                Listener listener = (Listener) user;

                // uncomment here
                if (!Objects.requireNonNull(listener).hasLoadedAFile()
                        /*|| listener.getPlayer().hasFinished(timestamp)*/) {
                    continue;
                }

                if (listener.getPlayer().getLoadedFile().getFileType() == FileType.PODCAST
                        && ((Podcast) listener.getPlayer().getLoadedFile()).getOwner().equals(getUsername())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void removeAnnouncement(Announcement announcement) {
        getAnnouncements().remove(announcement);
    }

    public void removePodcast(Podcast podcast) {
        getPodcasts().remove(podcast);
    }
}
