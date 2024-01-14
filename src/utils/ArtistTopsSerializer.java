package utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import data.entities.users.contentCreator.Artist;

import java.io.IOException;

public final class ArtistTopsSerializer extends JsonSerializer<Artist.ArtistTops> {
    @Override
    public void serialize(final Artist.ArtistTops artistTops, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("topAlbums", artistTops.getTopAlbums());
        jsonGenerator.writeObjectField("topSongs", artistTops.getTopSongs());
        jsonGenerator.writeObjectField("topFans", artistTops.getTopFans());
        jsonGenerator.writeObjectField("listeners", artistTops.getListeners());
        jsonGenerator.writeEndObject();
    }
}
