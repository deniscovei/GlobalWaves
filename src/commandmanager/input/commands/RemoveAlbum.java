package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioCollections.Album;
import data.entities.users.contentCreator.Artist;
import data.entities.users.User;
import utils.AppUtils.UserType;

public final class RemoveAlbum implements Command {
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
            Album album = Database.getInstance().findAlbum(input.getName());

            if (album == null) {
                message = input.getUsername() + " doesn't have an album with the given name.";
            } else if (album.interactingWithOthers(input.getTimestamp())) {
                message = input.getUsername() + " can't delete this album.";
            } else {
                artist.removeAlbum(album);
                message = "Album " + input.getName() + " was removed successfully.";
            }
        }

        return new Output(input, message);
    }
}
