package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioCollections.Album;
import data.entities.files.audioFiles.AudioFile;
import data.entities.files.audioFiles.Song;
import data.entities.users.contentCreator.Artist;
import data.entities.users.User;
import fileio.input.SongInput;
import utils.AppUtils;
import utils.AppUtils.UserType;

public final class AddAlbum implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.ARTIST) {
            message = input.getUsername() + " is not an artist.";
        } else {
            Artist artist = (Artist) user;

            if (artist.findAlbum(input.getName()) != null) {
                message = input.getUsername() + " has another album with the same name.";
            } else if (AppUtils.hasDuplicates(input.getSongs(), SongInput::getName)) {
                message = input.getUsername() + " has the same song at least twice in this album.";
            } else {
                Album newAlbum = new Album(input.getName(), input.getUsername(),
                                           input.getReleaseYear(), input.getDescription(),
                                           input.getSongs());
                artist.addAlbum(newAlbum);
                Database.getInstance().getAlbums().add(newAlbum);

                for (AudioFile audioFile : newAlbum.getAudioFiles()) {
                    Song song = (Song) audioFile;
                    Database.getInstance().addSong(song);
                }

                message = input.getUsername() + " has added new album successfully.";
            }
        }

        return new Output.Builder()
                .command(input.getCommand())
                .timestamp(input.getTimestamp())
                .user(input.getUsername())
                .message(message)
                .build();
    }
}
