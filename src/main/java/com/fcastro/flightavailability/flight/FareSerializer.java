package com.fcastro.flightavailability.flight;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.*;

public class FareSerializer extends StdSerializer<List<Fare>> {

    private static Map<String, String> fareClass = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("FIF", "first"),
            new AbstractMap.SimpleEntry<>("CIF", "business"),
            new AbstractMap.SimpleEntry<>("YIF", "economy"));


    public FareSerializer() {
        this(null);
    }

    public FareSerializer(Class<List<Fare>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Fare> value,
            JsonGenerator gen,
            SerializerProvider arg2)
            throws IOException {

        /*
        "farePrices":{"first":{
                       "ticket":{"currency":"EUR","amount":"272.00"},
                       "bookingFee":{"currency":"EUR","amount":"17.00"},
                       "tax":{"currency":"EUR","amount":"13.60"}
                      },
         */
        gen.writeStartObject("farePrices");
        for (var fare: value) {
            String _fareClass = fareClass.get(fare.getClassname());
            gen.writeFieldName(_fareClass == null? "" : _fareClass);
            gen.writeStartObject();

            gen.writeFieldName("ticket");
            gen.writeStartObject();
            writePrice(fare.getTicket(), gen);
            gen.writeEndObject();

            gen.writeFieldName("bookingFee");
            gen.writeStartObject();
            writePrice(fare.getBookingFee(), gen);
            gen.writeEndObject();

            gen.writeFieldName("tax");
            gen.writeStartObject();
            writePrice(fare.getTax(), gen);
            gen.writeEndObject();

            gen.writeEndObject();
        }
        gen.writeEndObject();
    }

    private void writePrice(FareDetail fareDetail,
                            JsonGenerator gen)
            throws IOException {

        gen.writeStringField("currency", fareDetail.getCurrency());
        gen.writeStringField("amount", fareDetail.getAmount());
    }
}