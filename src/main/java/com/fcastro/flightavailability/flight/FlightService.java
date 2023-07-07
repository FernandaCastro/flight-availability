package com.fcastro.flightavailability.flight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fcastro.flightavailability.xml.schema.AirlineWebClient;
import com.fcastro.flightavailability.xml.schema.FlightXml;
import com.fcastro.flightavailability.xml.schema.FlightXmlMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FlightService {

    private final AirlineWebClient airlineWebClient;
    private final XmlMapper xmlMapper;
    private final FlightXmlMapper flightXmlMapper;

    public FlightService(AirlineWebClient airlineWebClient, XmlMapper xmlMapper, FlightXmlMapper flightXmlMapper) {
        this.airlineWebClient = airlineWebClient;
        this.xmlMapper = xmlMapper;
        this.flightXmlMapper = flightXmlMapper;
    }

    public Map<String, List<Flight>> getAvailableFlights(String origin,
                                                         String destination,
                                                         String departureDate,
                                                         String returnDate,
                                                         Integer passengers) throws JsonProcessingException {

        var xml = airlineWebClient.getXmlAvailableFlights(origin, destination, departureDate, returnDate, passengers);

        try {
            List<FlightXml> flights = xmlMapper.readValue(xml, new TypeReference<>() {
            });
            return Map.of("availability", flightXmlMapper.mapFlightXmlToFlight(flights));

        }catch (JsonProcessingException e){
            throw e;
            //TODO: Create business exception
        }
    }
}
