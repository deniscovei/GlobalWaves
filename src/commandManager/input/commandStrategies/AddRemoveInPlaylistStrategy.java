package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.Song;
import data.entities.user.User;
import utils.Constants;

public final class AddRemoveInPlaylistStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        Playlist playlist = Database.getInstance().findPlaylist(input.getPlaylistId());
        String message;

        if (!user.hasLoadedAFile()) {
            message = "Please load a source before adding to or removing from the playlist.";
        } else if (playlist == null) {
            message = "The specified playlist does not exist.";
        } else {
            File selection = user.getSelection();
            Constants.FileType fileType = selection.getFileType();
            if (!fileType.equals(Constants.FileType.SONG)) {
                message = "The loaded source is not a song.";
            } else {
                Song song = (Song) selection;
                if (playlist.getSongs().contains(song)) {
                    playlist.removeSong(song);
                    message = "Successfully removed from playlist.";
                } else {
                    message = "Successfully added to playlist.";
                    playlist.addSong(song);
                }
            }
        }
        return new Output(input, message);
    }
}
