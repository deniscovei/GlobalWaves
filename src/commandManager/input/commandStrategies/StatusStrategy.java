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
    public Output action(Input inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        Player player = user.getPlayer();
        Stats stats = new Stats();

        System.out.println("STATUS timestamp: " + inputCommand.getTimestamp());
        if (user.hasLoadedAFile() && !player.hasFinished(inputCommand.getTimestamp())) {
            System.out.println("PUTEM DA STATUS");
            AudioFile currentPlayingFile = player.getCurrentPlayingFile(inputCommand.getTimestamp());
            stats.setName(currentPlayingFile.getName());
            stats.setPaused(player.isPaused());
            stats.setRemainedTime(player.getRemainedTime(currentPlayingFile, inputCommand.getTimestamp()));
            System.out.println("Name: " + currentPlayingFile.getName());
            System.out.println("Repeat State: " + player.getRepeatState());
            System.out.println("Loaded file: " + user.getLoadedFile().getName());
            stats.setRepeat(Constants.repeatStateToString(player.getRepeatState(), user.getLoadedFile().getFileType()));
        } else {
            System.out.println("NU PUTEM DA STATUS");
            System.out.println("timestamp: " + inputCommand.getTimestamp());
            stats.setPaused(true);
        }

        return new Output(inputCommand, stats);
    }
}
