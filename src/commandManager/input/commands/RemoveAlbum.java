package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Album;
import data.entities.users.Artist;
import data.entities.users.User;
import utils.Extras.UserType;

public final class RemoveAlbum implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        Album album = Database.getInstance().findAlbum(input.getName());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.ARTIST) {
            message = input.getUsername() + " is not an artist.";
        } else if (album == null) {
            message = input.getUsername() + " doesn't have an album with the given name.";
        } else if (album.interactingWithOthers(input.getTimestamp())) {
            message = input.getUsername() + " can't delete this album.";
        } else {
            ((Artist) user).removeAlbum(album);
            Database.getInstance().removeAlbum(album);
            message = "Album " + input.getName() + " was removed successfully.";
        }

        return new Output(input, message);
    }
}
