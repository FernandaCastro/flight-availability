package com.fcastro.flightavailability.xml.schema;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FlightXml {
    @JacksonXmlProperty(localName = "CarrierCode")
    private String carrierCode;

    @JacksonXmlProperty(localName = "FlightDesignator")
    private String flightDesignator;

    @JacksonXmlProperty(localName = "OriginAirport")
    private String originAirport;

    @JacksonXmlProperty(localName = "DestinationAirport")
    private String destinationAirport;

    @JacksonXmlProperty(localName = "DepartureDate")
    private String departureDate;

    @JacksonXmlProperty(localName = "ArrivalDate")
    private String arrivalDate;

    @JacksonXmlElementWrapper(localName = "Fares")
    @JacksonXmlProperty(localName = "Fare")
    private List<FareXml> fares;
}
