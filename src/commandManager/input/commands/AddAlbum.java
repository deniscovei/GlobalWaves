package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Album;
import data.entities.audio.audioFiles.Song;
import data.entities.user.Artist;
import fileio.input.SongInput;
import utils.Constants.UserType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class AddAlbum implements Command {
    @Override
    public Output action(Input input) {
        Artist user = (Artist) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.ARTIST) {
            message = input.getUsername() + " is not an artist.";
        } else {
            Album album = user.findAlbum(input.getName());
            if (album != null) {
                message = input.getUsername() + " has another album with the same name.";
            } else if (hasDuplicates(input.getSongs())) {
                message = input.getUsername() + " has the same song at least twice in this album.";
            } else {
                Album newAlbum = new Album(input.getName(), input.getUsername(),input.getReleaseYear(),
                                           input.getSongs());
                user.addAlbum(newAlbum);

                for (SongInput songInput : input.getSongs()) {
                    Database.getInstance().addSong(new Song(songInput));
                }

                message = input.getUsername() + " has added new album successfully.";
            }
        }

        return new Output(input, message);
    }

    private boolean hasDuplicates(ArrayList<SongInput> songs) {
        Set<String> songNames = new HashSet<>();
        for (SongInput songInput : songs) {
            if (songNames.contains(songInput.getName())) {
                return true;
            }
            songNames.add(songInput.getName());
        }

        return false;
    }
}
