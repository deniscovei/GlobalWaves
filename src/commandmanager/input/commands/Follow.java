package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.File;
import data.entities.files.audioCollections.Playlist;
import data.entities.users.Listener;
import utils.AppUtils;

import java.util.Objects;

/**
 * This class represents the strategy for following or unfollowing a playlist.
 */
public final class Follow implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        File selectedFile = user.getSelectedFile();
        String message;

        if (selectedFile == null) {
            message = "Please select a source before following or unfollowing.";
        } else if (!selectedFile.getFileType().equals(AppUtils.FileType.PLAYLIST)) {
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
