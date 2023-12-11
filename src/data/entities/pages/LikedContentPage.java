package data.entities.pages;

import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.Song;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.Extras.PageType;

import java.util.ArrayList;

public final class LikedContentPage extends Page {
    public LikedContentPage(User creator) {
        super(creator);
        pageType = PageType.LIKED_CONTENT_PAGE;
    }

    @Override
    public String getFormat() {
        Listener listener = (Listener) getCreator();
        StringBuilder result = new StringBuilder("Liked songs:\n\t[");

        for (Song song : listener.getLikedSongs()) {
            result.append(song.getName()).append(" - ").append(song.getArtist());
            if (song != listener.getLikedSongs().get(listener.getLikedSongs().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nFollowed playlists:\n\t[");

        ArrayList<Playlist> followedPlaylists = listener.getFollowedPlaylists();

        for (Playlist playlist : followedPlaylists) {
            result.append(playlist.getName()).append(" - ").append(playlist.getOwner());
            if (playlist != followedPlaylists.get(followedPlaylists.size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }
}
