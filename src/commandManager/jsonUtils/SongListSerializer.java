package commandManager.jsonUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import data.entities.audio.audioFiles.Song;

import java.io.IOException;
import java.util.ArrayList;

public class SongListSerializer extends JsonSerializer<ArrayList<Song>> {
    @Override
    public void serialize(ArrayList<Song> songs, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        ArrayList<String> songNames = new ArrayList<>();

        for (Song song : songs) {
            songNames.add(song.getName());
        }

        jsonGenerator.writeObject(songNames);
    }
}