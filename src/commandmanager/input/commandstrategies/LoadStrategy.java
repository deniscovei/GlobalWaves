package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.entities.audio.File;
import utils.Constants;
import data.Database;
import data.entities.audio.audiocollections.Playlist;
import data.entities.audio.audiocollections.Podcast;
import data.entities.user.User;

import java.util.Objects;

/**
 * This class represents the strategy for loading a file.
 */
public final class LoadStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
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
