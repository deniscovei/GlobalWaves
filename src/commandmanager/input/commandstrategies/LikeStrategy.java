package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.audio.audiofiles.AudioFile;
import data.entities.audio.audiofiles.Song;
import data.entities.user.User;
import utils.Constants;

import java.util.Objects;

/**
 * This class represents the strategy for liking or unliking a song.
 */
public final class LikeStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).hasLoadedAFile()
                || user.getPlayer().hasFinished(input.getTimestamp())) {
            return new Output(input, "Please load a source before liking or unliking.");
        } else {
            AudioFile currentPlayingFile = user.getPlayer()
                    .getCurrentPlayingFile(input.getTimestamp());
            Constants.FileType fileType = currentPlayingFile.getFileType();
            if (!fileType.equals(Constants.FileType.SONG)) {
                return new Output(input, "Loaded source is not a song.");
            } else {
                Song song = (Song) currentPlayingFile;
                if (song.getUsersWhoLiked().contains(user)) {
                    user.unlike(song);
                    song.registerUnlike(user);
                    return new Output(input, "Unlike registered successfully.");
                } else {
                    user.like(song);
                    song.registerLike(user);
                    return new Output(input, "Like registered successfully.");
                }
            }
        }
    }
}
