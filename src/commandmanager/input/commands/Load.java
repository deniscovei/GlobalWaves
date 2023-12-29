package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.entities.files.File;
import data.entities.files.audioCollections.AudioCollection;
import data.entities.users.Listener;
import utils.AppUtils;
import data.Database;

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
            AppUtils.FileType fileType = selection.getFileType();
            if (AppUtils.isAudioCollection(fileType)
                && ((AudioCollection) selection).getAudioFiles().isEmpty()) {
                message = "You can't load an empty audio collection!";
            } else {
                //System.out.print("TIME " + input.getTimestamp() + " ");
                user.loadAudioFile(input.getTimestamp());
                message = "Playback loaded successfully.";
            }
        }

        return new Output(input, message);
    }
}
