package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audiocollections.Playlist;
import data.entities.audio.audiofiles.Song;
import data.entities.user.User;
import utils.Constants;

/**
 * This class represents the strategy used when the user wants to add or remove a song from a
 * playlist.
 */
public final class AddRemoveInPlaylistStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        Playlist playlist = Database.getInstance().findPlaylist(input.getPlaylistId(),
                input.getUsername());
        String message;

        if (!user.hasLoadedAFile()) {
            message = "Please load a source before adding to or removing from the playlist.";
        } else if (playlist == null) {
            message = "The specified playlist does not exist.";
        } else {
            File loadedFile = user.getLoadedFile();
            Constants.FileType fileType = loadedFile.getFileType();
            if (!fileType.equals(Constants.FileType.SONG)) {
                message = "The loaded source is not a song.";
            } else {
                Song song = (Song) loadedFile;
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
