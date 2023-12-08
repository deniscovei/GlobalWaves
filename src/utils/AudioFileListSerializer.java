package utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import data.entities.audio.audioFiles.AudioFile;

import java.io.IOException;
import java.util.ArrayList;

public final class AudioFileListSerializer extends JsonSerializer<ArrayList<AudioFile>> {
    @Override
    public void serialize(final ArrayList<AudioFile> audioFiles, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();

        for (AudioFile audioFile : audioFiles) {
            jsonGenerator.writeString(audioFile.getName());
        }

        jsonGenerator.writeEndArray();
    }
}
