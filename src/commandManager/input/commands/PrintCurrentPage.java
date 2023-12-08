package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.Song;
import data.entities.user.Artist;
import data.entities.user.Listener;
import data.entities.user.User;
import utils.Constants.UserType;

import java.util.ArrayList;
import java.util.Objects;

public final class PrintCurrentPage implements Command {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        if (Objects.requireNonNull(user).getUserType().equals(UserType.LISTENER) && !((Listener) user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;
        switch (user.getCurrentPage()) {
            case HOME_PAGE:
                message = homePageFormat((Listener) user);
                break;
            case LIKED_CONTENT_PAGE:
                message = likedContentPageFormat(user);
                break;
            case ARTIST_PAGE:
                message = artistPageFormat((Artist) user);
                break;
            case HOST_PAGE:
                message = hostPageFormat(user);
                break;
            default:
                message = "Invalid page.";
        }

        return new Output(input, message);
    }

    // Liked songs:\n\t[songname1, songname2, …]\n\nFollowed playlists:\n\t[playlistname1, playlistname2, …]
    private String homePageFormat(Listener user) {
        StringBuilder result = new StringBuilder("Liked songs:\n\t[");

        for (Song song : user.getLikedSongs()) {
            result.append(song.getName());
            if (song != user.getLikedSongs().get(user.getLikedSongs().size() - 1)) {
                result.append(",");
            }
        }

        result.append("]\n\nFollowed playlists:\n\t[");

        ArrayList<Playlist> followedPlaylists = user.getFollowedPlaylists();

        for (Playlist playlist : followedPlaylists) {
            result.append(playlist.getName());
            if (playlist != followedPlaylists.get(followedPlaylists.size() - 1)) {
                result.append(",");
            }
        }

        result.append("]");
        return result.toString();
    }

    private String likedContentPageFormat(User user) {
        return "";
    }

    // Albums:\n\t[albumname1, albumname2, …]\n\nMerch:\n\t[merchname1 - merchprice1:\n\tmerchdescription1,
    // merchname2 - merchprice2:\n\tmerchdescription2, …]\n\nEvent:\n\t[eventname1 - eventdate1:\n\t
    // eventdescription1, eventname2 - eventdate2:\n\teventdescription2, …]
    private String artistPageFormat(Artist user) {
        return "";
    }

    private String hostPageFormat(User user) {
        return "";
    }
}
