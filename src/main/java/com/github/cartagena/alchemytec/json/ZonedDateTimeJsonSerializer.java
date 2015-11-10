package com.github.cartagena.alchemytec.json;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ZonedDateTimeJsonSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(final ZonedDateTime value, final JsonGenerator generator, final SerializerProvider provider) throws IOException {
        generator.writeString(ISO_INSTANT.format(value));
    }

}
