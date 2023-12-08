package commandManager.input.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.user.Artist;
import fileio.output.AlbumOutput;
import lombok.Getter;

import java.util.ArrayList;

public final class ShowAlbums implements Command {
    @Override
    public Output action(Input input) {
        Artist artist = (Artist) Database.getInstance().findUser(input.getUsername());

        ArrayList<Object> result = new ArrayList<>();
        for (int i = 0; i < artist.getAlbums().size(); i++) {
            result.add(new AlbumOutput(artist, i));
        }

        return new Output(input, result, null);
    }
}
