package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.entities.audio.File;
import data.entities.user.Listener;
import utils.Constants;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioCollections.Podcast;

import java.util.Objects;

/**
 * This class represents the strategy for loading a file.
 */
public final class Load implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;

        if (Objects.requireNonNull(user).getSelectedFile() == null) {
            message = "Please select a source before attempting to load.";
        } else {
            File selection = user.getSelectedFile();
            Constants.FileType fileType = selection.getFileType();
            if (fileType.equals(Constants.FileType.PODCAST)
                && ((Podcast) selection).getEpisodes().isEmpty()
                || fileType.equals(Constants.FileType.PLAYLIST)
                && ((Playlist) selection).getSongs().isEmpty()) {
                message = "You can't load an empty audio collection!";
            } else {
                user.loadAudioFile(input.getTimestamp());
                message = "Playback loaded successfully.";
            }
        }

        return new Output(input, message);
    }
}
