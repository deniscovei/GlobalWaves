package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioCollections.AudioCollection;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import data.entities.users.Listener;
import utils.Extras.FileType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the strategy used when the user wants to add or remove a song from a
 * playlist.
 */
public final class AddRemoveInPlaylist implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        Playlist playlist = Database.getInstance().findPlaylist(input.getPlaylistId(),
                input.getUsername());
        String message;

        if (!user.hasLoadedAFile()) {
            message = "Please load a source before adding to or removing from the playlist.";
        } else if (playlist == null) {
            message = "The specified playlist does not exist.";
        } else {
            File loadedFile = user.getLoadedFile();
            FileType fileType = loadedFile.getFileType();
            if (fileType != FileType.SONG && fileType != FileType.ALBUM) {
                message = "The loaded source is not a song.";
            } else {
                Song song = (fileType == FileType.SONG ? (Song) loadedFile :
                        (Song) user.getPlayer().getCurrentPlayingFile(input.getTimestamp()));

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
