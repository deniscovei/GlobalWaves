package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;

public class LikeStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());

        if (!user.hasLoadedAudioFile()) {
            return new OutputCommand(inputCommand, "Please load a source before liking or unliking.");
        } else if (!(user.getSelection() instanceof Song song)) {
            return new OutputCommand(inputCommand, "Loaded source is not a song.");
        } else {
            if (song.getUserWhoLiked().contains(user)) {
                user.unlike(song);
                song.registerUnlike(user);
                return new OutputCommand(inputCommand, "Unlike registered successfully.");
            } else {
                user.like(song);
                song.registerLike(user);
                return new OutputCommand(inputCommand, "Like registered successfully.");
            }
        }
    }
}
