package data.entities.users;

import data.Database;
import data.entities.audio.audioCollections.Album;
import data.entities.audio.audioCollections.AudioCollection;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import data.entities.content.Event;
import data.entities.content.Merch;
import data.entities.pages.ArtistPage;
import lombok.Getter;
import lombok.Setter;
import utils.Extras.UserType;
import utils.Extras.FileType;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class Artist extends User {
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Merch> merches = new ArrayList<>();

    public Artist(String username, int age, String city) {
        super(username, age, city);
        setUserType(UserType.ARTIST);
        setCurrentPage(new ArtistPage(this));
    }

    public Album findAlbum(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    public void addAlbum(Album album) {
        getAlbums().add(album);
    }

    public Event findEvent(String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public void addEvent(Event event) {
        getEvents().add(event);
    }

    public Merch findMerch(String name) {
        for (Merch merch : merches) {
            if (merch.getName().equals(name)) {
                return merch;
            }
        }
        return null;
    }

    public void addMerch(Merch merch) {
        getMerches().add(merch);
    }

    @Override
    public boolean interactingWithOthers(int timestamp) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == UserType.LISTENER) {
                Listener listener = (Listener) user;
                if (!Objects.requireNonNull(listener).hasLoadedAFile()
                    || listener.getPlayer().hasFinished(timestamp)) {
                    continue;
                }

                AudioFile currentPlayingFile = listener.getPlayer().getCurrentPlayingFile(timestamp);
                if (currentPlayingFile.getFileType().equals(FileType.SONG)
                    && ((Song) currentPlayingFile).getArtist().equals(getUsername())) {
                    return true;
                }

                return true;
            }
        }

        return false;
    }

    public void removeAlbum(Album album) {
        getAlbums().remove(album);
    }

    public void removeEvent(Event event) {
        getEvents().remove(event);
    }

    public int getNumberOfLikes() {
        int numberOfLikes = 0;
        for (Album album : getAlbums()) {
            numberOfLikes += album.getNumberOfLikes();
        }

        return numberOfLikes;
    }
}
