package utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import data.entities.users.Listener;

import java.io.IOException;

public final class ListenerTopsSerializer extends JsonSerializer<Listener.ListenerTops> {
    @Override
    public void serialize(final Listener.ListenerTops listenerTops,
                          final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("topArtists", listenerTops.getTopArtists());
        jsonGenerator.writeObjectField("topGenres", listenerTops.getTopGenres());
        jsonGenerator.writeObjectField("topSongs", listenerTops.getTopSongs());
        jsonGenerator.writeObjectField("topAlbums", listenerTops.getTopAlbums());
        jsonGenerator.writeObjectField("topEpisodes", listenerTops.getTopEpisodes());
        jsonGenerator.writeEndObject();
    }
}
