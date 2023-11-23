package commandmanager.input.commandstrategies;

import commandmanager.input.attributes.Stats;
import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.user.audioplayer.Player;
import data.entities.audio.audiofiles.AudioFile;
import data.entities.user.User;
import utils.Constants;

import java.util.Objects;

/**
 * This class implements the command strategy for showing the stats of the player.
 */
public final class StatusStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        Player player = Objects.requireNonNull(user).getPlayer();
        Stats stats = new Stats();

        if (user.hasLoadedAFile() && !player.hasFinished(input.getTimestamp())) {
            AudioFile currentPlayingFile = player.getCurrentPlayingFile(input.getTimestamp());
            stats.setName(currentPlayingFile.getName());
            stats.setPaused(player.isPaused());
            stats.setRemainedTime(player.getRemainedTime(currentPlayingFile,
                    input.getTimestamp()));
            stats.setRepeat(Constants.repeatStateToString(player.getRepeatState(),
                    user.getLoadedFile().getFileType()));
            stats.setShuffle(player.isShuffleActivated());
        } else {
            stats.setPaused(true);
        }

        return new Output(input, stats);
    }
}
