package com.fcastro.flightavailability.flight;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FareDeserializer extends StdDeserializer<List<Fare>> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public FareDeserializer() {
        this(null);
    }

    public FareDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Fare> deserialize(JsonParser jsonparser,
                                  DeserializationContext context)
            throws IOException {

        /*
        "farePrices":{"first":{
                       "ticket":{"currency":"EUR","amount":"272.00"},
                       "bookingFee":{"currency":"EUR","amount":"17.00"},
                       "tax":{"currency":"EUR","amount":"13.60"}
                      },
         */
        List<Fare> farePrices = new ArrayList<>();
        JsonNode root = jsonparser.getCodec().readTree(jsonparser);

        var firstClassRoot = root.path("farePrices").path("first");
        if(!firstClassRoot.isNull()){
            farePrices.add(createFare(firstClassRoot));
        }

        var businessClassRoot = root.path("farePrices").path("business");
        if(!businessClassRoot.isNull()){
            farePrices.add(createFare(businessClassRoot));
        }

        var economyClassRoot = root.path("farePrices").path("economy");
        if(!economyClassRoot.isNull()){
            farePrices.add(createFare(firstClassRoot));
        }

        return farePrices;
    }

    private Fare createFare(JsonNode classRoot){
        String classname = classRoot.asText();
        FareDetail ticket = new FareDetail(classRoot.path("ticket").path("currency").asText(), classRoot.path("amount").asText());
        FareDetail bookingFee = new FareDetail(classRoot.path("bookingFee").path("currency").asText(), classRoot.path("amount").asText());
        FareDetail tax = new FareDetail(classRoot.path("tax").path("currency").asText(), classRoot.path("amount").asText());
        return new Fare(classname, ticket, bookingFee, tax);
    }
}