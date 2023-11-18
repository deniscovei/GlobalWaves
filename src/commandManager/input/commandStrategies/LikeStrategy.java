package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;
import utils.Constants;

public class LikeStrategy implements CommandStrategy {
    public Output action(Input inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());

        if (!user.hasLoadedAFile()) {
            return new Output(inputCommand, "Please load a source before liking or unliking.");
        } else {
            AudioFile currentPlayingFile = user.getPlayer().getCurrentPlayingFile(inputCommand.getTimestamp());
            Constants.FileType fileType = currentPlayingFile.getFileType();
            if (!fileType.equals(Constants.FileType.SONG)) {
                return new Output(inputCommand, "Loaded source is not a song.");
            } else {
                Song song = (Song) currentPlayingFile;
                if (song.getUserWhoLiked().contains(user)) {
                    user.unlike(song);
                    song.registerUnlike(user);
                    return new Output(inputCommand, "Unlike registered successfully.");
                } else {
                    user.like(song);
                    song.registerLike(user);
                    return new Output(inputCommand, "Like registered successfully.");
                }
            }
        }
    }
}
