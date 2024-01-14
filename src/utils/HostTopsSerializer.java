package utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import data.entities.users.contentCreator.Host;

import java.io.IOException;

public final class HostTopsSerializer extends JsonSerializer<Host.HostTops> {
    @Override
    public void serialize(final Host.HostTops hostTops, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("topEpisodes", hostTops.getTopEpisodes());
        jsonGenerator.writeObjectField("listeners", hostTops.getListeners());
        jsonGenerator.writeEndObject();
    }
}
