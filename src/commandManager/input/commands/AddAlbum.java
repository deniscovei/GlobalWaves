package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Album;
import data.entities.audio.audioFiles.Song;
import data.entities.users.Artist;
import data.entities.users.User;
import fileio.input.SongInput;
import utils.Extras;
import utils.Extras.UserType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class AddAlbum implements Command {
    @Override
    public Output action(Input input) {
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
            } else if (Extras.hasDuplicates(input.getSongs(), SongInput::getName)) {
                message = input.getUsername() + " has the same song at least twice in this album.";
            } else {
                Album newAlbum = new Album(input.getName(), input.getUsername(), input.getReleaseYear(),
                        input.getDescription(), input.getSongs());
                artist.addAlbum(newAlbum);
                Database.getInstance().getAlbums().add(newAlbum);

                for (SongInput songInput : input.getSongs()) {
                    Database.getInstance().addSong(new Song(songInput, true));
                }

                message = input.getUsername() + " has added new album successfully.";
            }
        }

        return new Output(input, message);
    }
}
