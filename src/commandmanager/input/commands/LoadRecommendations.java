package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.File;
import data.entities.files.audioCollections.AudioCollection;
import data.entities.files.audioFiles.Song;
import data.entities.users.Listener;
import utils.AppUtils;

import java.util.Objects;

public class LoadRecommendations implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        String message;

        if (user.getLastRecommendation() == null) {
            message = "No recommendations available.";
        } else {
            user.loadAudioFile(input.getTimestamp(), true);
            user.getLoadedFile().listen(user);
            message = "Playback loaded successfully.";
        }

        return new Output(input, message);
    }
}
