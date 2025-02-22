package commandmanager.input.commands;

import commandmanager.input.attributes.Stats;
import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;
import data.entities.player.Player;
import data.entities.files.audioFiles.AudioFile;
import utils.AppUtils;

import java.util.Objects;

/**
 * This class implements the command strategy for showing the stats of the player.
 */
public final class Status implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        Player player = Objects.requireNonNull(user).getPlayer();
        Stats stats;

        if (!user.isOnline()) {
            stats = user.getStats();
        } else {
            stats = new Stats();

            if (user.hasLoadedAFile() && !player.hasFinished(input.getTimestamp())) {
                AudioFile currentPlayingFile = player.getCurrentPlayingFile(input.getTimestamp());
                stats.setName(currentPlayingFile.getName());
                stats.setPaused(player.isPaused());
                stats.setRemainedTime(player.getRemainedTime(currentPlayingFile,
                    input.getTimestamp()));
                stats.setRepeat(AppUtils.repeatStateToString(player.getRepeatState(),
                    user.getLoadedFile().getFileType()));
                stats.setShuffle(player.isShuffleActivated());
            } else {
                stats.setPaused(true);
            }
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .stats(stats)
            .build();
    }
}
