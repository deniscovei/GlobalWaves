package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioFiles.AudioFile;
import data.entities.files.audioFiles.Song;
import data.entities.users.Listener;
import utils.AppUtils.FileType;

import java.util.Objects;

/**
 * This class represents the strategy for liking or unliking a song.
 */
public final class Like implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!Objects.requireNonNull(user).isOnline()) {
            message = user.getUsername() + " is offline.";
        } else if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = "Please load a source before liking or unliking.";
        } else {
            AudioFile currentPlayingFile = user.getPlayer()
                    .getCurrentPlayingFile(input.getTimestamp());

            if (currentPlayingFile.getFileType() != FileType.SONG) {
                message = "Loaded source is not a song.";
            } else {
                Song song = (Song) currentPlayingFile;

                if (song.getUsersWhoLiked().contains(user)) {
                    user.unlike(song);
                    song.registerUnlike(user);
                    message = "Unlike registered successfully.";
                } else {
                    user.like(song);
                    song.registerLike(user);
                    message = "Like registered successfully.";
                }
            }
        }

        return new Output.Builder()
                .command(input.getCommand())
                .timestamp(input.getTimestamp())
                .user(input.getUsername())
                .message(message)
                .build();
    }
}
