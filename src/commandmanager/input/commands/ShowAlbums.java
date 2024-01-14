package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.contentCreator.Artist;
import fileio.output.AlbumOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ShowAlbums implements Command {
    @Override
    public Output action(final Input input) {
        Artist artist = (Artist) Database.getInstance().findUser(input.getUsername());

        List<AlbumOutput> result = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(artist).getAlbums().size(); i++) {
            result.add(new AlbumOutput(artist, i));
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .result(result)
            .build();
    }
}
