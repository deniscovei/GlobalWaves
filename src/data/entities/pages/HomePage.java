package data.entities.pages;

import data.entities.files.audioCollections.Playlist;
import data.entities.files.audioFiles.Song;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils;
import utils.AppUtils.PageType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type Home page.
 */
public final class HomePage extends Page {
    /**
     * Instantiates a new Home page.
     *
     * @param creator the creator
     */
    public HomePage(final User creator) {
        super(creator);
        pageType = PageType.HOME_PAGE;
    }

    @Override
    public String getFormat() {
        Listener listener = (Listener) getCreator();
        StringBuilder result = new StringBuilder("Liked songs:\n\t[");
        List<Song> likedSongs = new ArrayList<>(listener.getLikedSongs());

        likedSongs.sort(Comparator
                .<Song, Integer>comparing(song -> song.getUsersWhoLiked().size())
                .reversed());

        int resCount = 0;

        for (Song song : likedSongs) {
            result.append(song.getName());

            if (++resCount == AppUtils.RES_COUNT_MAX) {
                break;
            }

            if (song != likedSongs.get(likedSongs.size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nFollowed playlists:\n\t[");

        List<Playlist> followedPlaylists = listener.getFollowedPlaylists();

        followedPlaylists.sort(Comparator
                .comparingInt(Playlist::getFollowers).reversed()
                .thenComparing(Playlist::getCreatedAt));

        resCount = 0;

        for (Playlist playlist : followedPlaylists) {
            result.append(playlist.getName());

            if (++resCount == AppUtils.RES_COUNT_MAX) {
                break;
            }

            if (playlist != followedPlaylists.get(followedPlaylists.size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nSong recommendations:\n\t[");

        for (Song song : listener.getSongRecommendations()) {
            result.append(song.getName());

            if (++resCount == AppUtils.RES_COUNT_MAX) {
                break;
            }

            if (song != listener.getSongRecommendations()
                .get(listener.getSongRecommendations().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nPlaylists recommendations:\n\t[");

        for (Playlist playlist : listener.getPlaylistsRecommendations()) {
            result.append(playlist.getName());

            if (++resCount == AppUtils.RES_COUNT_MAX) {
                break;
            }

            if (playlist != listener.getPlaylistsRecommendations()
                .get(listener.getPlaylistsRecommendations().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]");

        return result.toString();
    }
}
