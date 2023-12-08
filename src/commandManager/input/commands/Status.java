package commandManager.input.commands;

import commandManager.input.attributes.Stats;
import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.Listener;
import data.entities.audioPlayer.Player;
import data.entities.audio.audioFiles.AudioFile;
import utils.Constants;

import java.util.Objects;

/**
 * This class implements the command strategy for showing the stats of the player.
 */
public final class Status implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        Player player = Objects.requireNonNull(user).getPlayer();
        Stats stats = new Stats();

        if (!user.isOnline()) {
            return new Output(input, user.getStats());
        }

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
