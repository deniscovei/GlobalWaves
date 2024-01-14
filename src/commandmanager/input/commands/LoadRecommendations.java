package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

import java.util.Objects;

public final class LoadRecommendations implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (user.getLastRecommendation() == null) {
            message = "No recommendations available.";
        } else {
            user.loadAudioFile(input.getTimestamp(), true);
            user.getLoadedFile().listen(user);
            message = "Playback loaded successfully.";
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
