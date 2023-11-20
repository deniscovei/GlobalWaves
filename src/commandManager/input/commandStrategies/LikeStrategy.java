package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;
import utils.Constants;

public class LikeStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());

        if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            return new Output(input, "Please load a source before liking or unliking.");
        } else {
            AudioFile currentPlayingFile = user.getPlayer().getCurrentPlayingFile(input.getTimestamp());
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
