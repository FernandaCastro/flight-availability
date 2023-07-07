package com.fcastro.flightavailability.flight;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ArriveDepartureDeserializer extends StdDeserializer<OffsetDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public ArriveDepartureDeserializer() {
        this(null);
    }

    public ArriveDepartureDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser jsonparser,
                                     DeserializationContext context)
            throws IOException {
        String date = jsonparser.getText();
        return OffsetDateTime.parse(date, formatter);
    }
}