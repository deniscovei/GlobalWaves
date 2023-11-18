package commandManager.input.commandStrategies;

import commandManager.input.attributes.Stats;
import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.user.audioPlayer.Player;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.user.User;
import utils.Constants;

public final class StatusStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        Player player = user.getPlayer();
        Stats stats = new Stats();

        System.out.println("STATUS timestamp: " + input.getTimestamp());
        if (user.hasLoadedAFile() && !player.hasFinished(input.getTimestamp())) {
            System.out.println("PUTEM DA STATUS");
            AudioFile currentPlayingFile = player.getCurrentPlayingFile(input.getTimestamp());
            stats.setName(currentPlayingFile.getName());
            stats.setPaused(player.isPaused());
            stats.setRemainedTime(player.getRemainedTime(currentPlayingFile, input.getTimestamp()));
            System.out.println("Name: " + currentPlayingFile.getName());
            System.out.println("Repeat State: " + player.getRepeatState());
            System.out.println("Loaded file: " + user.getLoadedFile().getName());
            stats.setRepeat(Constants.repeatStateToString(player.getRepeatState(), user.getLoadedFile().getFileType()));
            stats.setShuffle(player.isShuffleActivated());
        } else {
            System.out.println("NU PUTEM DA STATUS");
            System.out.println("timestamp: " + input.getTimestamp());
            stats.setPaused(true);
        }

        return new Output(input, stats);
    }
}
