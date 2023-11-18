package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.entities.audio.File;
import utils.Constants;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioCollections.Podcast;
import data.entities.user.User;

public final class LoadStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user.getSelectedFile() == null) {
            message = "Please select a source before attempting to load.";
        } else {
            File selection = user.getSelectedFile();
            Constants.FileType fileType = selection.getFileType();
            if (fileType.equals(Constants.FileType.PODCAST) && ((Podcast) selection).getEpisodes().isEmpty() ||
                fileType.equals(Constants.FileType.PLAYLIST) && ((Playlist) selection).getSongs().isEmpty()) {
                message = "You can't load an empty audio collection!";
            } else {
                user.loadAudioFile(input.getTimestamp());
                message = "Playback loaded successfully.";
            }
        }

        return new Output(input, message);
    }
}
