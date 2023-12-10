package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Artist;
import fileio.output.AlbumOutput;

import java.util.ArrayList;
import java.util.Objects;

public final class ShowAlbums implements Command {
    @Override
    public Output action(Input input) {
        Artist artist = (Artist) Database.getInstance().findUser(input.getUsername());

        ArrayList<Object> result = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(artist).getAlbums().size(); i++) {
            result.add(new AlbumOutput(artist, i));
        }

        return new Output(input, result, null);
    }
}