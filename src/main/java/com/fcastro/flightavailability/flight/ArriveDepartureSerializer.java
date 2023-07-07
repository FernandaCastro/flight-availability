package com.fcastro.flightavailability.flight;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ArriveDepartureSerializer extends StdSerializer<OffsetDateTime> {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mma");

    public ArriveDepartureSerializer() {
        this(null);
    }

    public ArriveDepartureSerializer(Class<OffsetDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(
            OffsetDateTime value,
            JsonGenerator gen,
            SerializerProvider arg2)
            throws IOException, JsonProcessingException {

        gen.writeStartObject();
        gen.writeStringField("date: ", dateFormatter.format(value));
        gen.writeStringField("time: ", timeFormatter.format(value));
        gen.writeEndObject();
    }
}