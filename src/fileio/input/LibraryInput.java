package fileio.input;

import java.util.List;

public final class LibraryInput {
    private List<SongInput> songs;
    private List<PodcastInput> podcasts;
    private List<UserInput> users;

    public LibraryInput() {
    }

    public List<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final List<SongInput> songs) {
        this.songs = songs;
    }

    public List<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final List<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public List<UserInput> getUsers() {
        return users;
    }

    public void setUsers(final List<UserInput> users) {
        this.users = users;
    }
}
