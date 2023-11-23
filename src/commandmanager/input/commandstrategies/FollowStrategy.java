package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audiocollections.Playlist;
import utils.Constants;

import java.util.Objects;

/**
 * This class represents the strategy for following or unfollowing a playlist.
 */
public final class FollowStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        File selectedFile = Objects.requireNonNull(Database.getInstance()
                .findUser(input.getUsername())).getSelectedFile();
        String message;

        if (selectedFile == null) {
            message = "Please select a source before following or unfollowing.";
        } else if (!selectedFile.getFileType().equals(Constants.FileType.PLAYLIST)) {
            message = "The selected source is not a playlist.";
        } else {
            Playlist playlist = (Playlist) selectedFile;
            if (playlist.getOwner().equals(input.getUsername())) {
                message = "You cannot follow or unfollow your own playlist.";
            } else if (playlist.getFollowerNames().contains(input.getUsername())) {
                playlist.getFollowerNames().remove(input.getUsername());
                message = "Playlist unfollowed successfully.";
            } else {
                playlist.getFollowerNames().add(input.getUsername());
                message = "Playlist followed successfully.";
            }
        }

        return new Output(input, message);
    }
}
