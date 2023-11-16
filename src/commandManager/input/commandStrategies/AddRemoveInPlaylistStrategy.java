package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;
import data.Database;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;

public final class AddRemoveInPlaylistStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        Playlist playlist = Database.getInstance().findPlaylist(inputCommand.getPlaylistId());

        if (!user.hasLoadedAudioFile()) {
            return new OutputCommand(inputCommand, "Please load a source before adding to or removing from the playlist.");
        } else if (playlist == null) {
            return new OutputCommand(inputCommand, "The specified playlist does not exist.");
        } else if (!(user.getSelection() instanceof Song song)) {
            return new OutputCommand(inputCommand, "The loaded source is not a song.");
        } else {
            if (playlist.getSongs().contains(song)) {
                playlist.removeSong(song);
                return new OutputCommand(inputCommand, "Successfully removed from playlist.");
            } else {
                playlist.addSong(song);
                return new OutputCommand(inputCommand, "Successfully added to playlist.");
            }
        }
    }
}
