package data.entities.audio.derived;

import data.entities.audio.base.AudioFile;
import data.entities.user.User;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Playlist extends AudioFile {
    private ArrayList <AudioFile> songs = new ArrayList<>();
    private User owner = null;
    private boolean visibility = false;

    public Playlist() {
    }

    public Playlist(final String name) {
        super(name);
    }

    public void setSongs(ArrayList<AudioFile> songs) {
        this.songs = songs;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
