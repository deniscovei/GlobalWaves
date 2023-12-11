package data.entities.pages;

import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.Song;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.Extras;
import utils.Extras.PageType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class HomePage extends Page {
    public HomePage(User creator) {
        super(creator);
        pageType = PageType.HOME_PAGE;
    }

    @Override
    public String getFormat() {
        Listener listener = (Listener) getCreator();
        StringBuilder result = new StringBuilder("Liked songs:\n\t[");
        ArrayList<Song> likedSongs = new ArrayList<>(listener.getLikedSongs());

        likedSongs.sort(Comparator
                .<Song, Integer>comparing(song -> song.getUsersWhoLiked().size())
                .reversed());

        int resCount = 0;

        for (Song song : likedSongs) {
            result.append(song.getName());

            if (++resCount == Extras.RES_COUNT_MAX) {
                break;
            }

            if (song != likedSongs.get(likedSongs.size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nFollowed playlists:\n\t[");

        ArrayList<Playlist> followedPlaylists = listener.getFollowedPlaylists();

        followedPlaylists.sort(Comparator
                .comparingInt(Playlist::getFollowers).reversed()
                .thenComparing(Playlist::getCreatedAt));

        resCount = 0;

        for (Playlist playlist : followedPlaylists) {
            result.append(playlist.getName());

            if (++resCount == Extras.RES_COUNT_MAX) {
                break;
            }

            if (playlist != followedPlaylists.get(followedPlaylists.size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }
}
