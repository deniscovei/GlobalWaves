package data.entities.users;

import data.entities.audio.audioCollections.Podcast;
import data.entities.content.Announcement;
import data.entities.pages.HostPage;
import lombok.Getter;
import lombok.Setter;
import utils.Extras.UserType;

import java.util.ArrayList;

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
        return false;
    }

    public void removeAnnouncement(Announcement announcement) {
        getAnnouncements().remove(announcement);
    }
}
