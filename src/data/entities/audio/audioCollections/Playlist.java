package data.entities.audio.audioCollections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import commandManager.jsonUtils.SongListSerializer;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Playlist extends AudioCollection {
    @JsonSerialize(using = SongListSerializer.class)
    private ArrayList <Song> songs = new ArrayList<>();
    private String visibility = "public";
    private int followers = 0;

    public Playlist() {
    }

    public Playlist(final String name) {
        super(name);
    }

    public Playlist(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void makePublic() {
        this.visibility = "public";
    }

    public void makePrivate() {
        this.visibility = "private";
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    @Override
    public void play(int timestamp) {

    }

    @JsonIgnore
    @Override
    public boolean isPlaying() {
        return true;
    }

    @Override
    public void pause(int timestamp) {

    }

    @Override
    public int getRemainedTime(int timestamp) {
        return 0;
    }
}
