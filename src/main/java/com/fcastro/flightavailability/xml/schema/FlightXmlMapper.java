package com.fcastro.flightavailability.xml.schema;

import com.fcastro.flightavailability.flight.Fare;
import com.fcastro.flightavailability.flight.FareDetail;
import com.fcastro.flightavailability.flight.Flight;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class FlightXmlMapper {

    public List<Flight> mapFlightXmlToFlight(List<FlightXml> flightsXml){
        List<Flight> flights = new ArrayList<>();

        for (FlightXml flightXml:flightsXml){
            flights.add(createFlight(flightXml));
        }

        return flights;
    }

    private Flight createFlight(FlightXml flightXml){
        List<Fare> fares = new ArrayList<>();

        for (FareXml fareXml : flightXml.getFares()){
            fares.add(Fare.builder()
                    .classname(fareXml.getClassname())
                    .ticket(createFareDetail(fareXml.getTicket()))
                    .bookingFee(createFareDetail(fareXml.getBookingFee()))
                    .tax(createFareDetail(fareXml.getTax()))
                    .build());
        }

        return Flight.builder()
                .operator(flightXml.getCarrierCode())
                .flightNumber(flightXml.getFlightDesignator())
                .departsFrom(flightXml.getOriginAirport())
                .arrivesAt(flightXml.getDestinationAirport())
                .departsOn(OffsetDateTime.parse(flightXml.getDepartureDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .arrivesOn(OffsetDateTime.parse(flightXml.getArrivalDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .farePrices(fares)
                .build();
    }

    private FareDetail createFareDetail(String price){

        if (price == null || price.isBlank()){
            return null;
        }

        String currency = "";
        String amount = "";
        var _price = price.split("\s");
        if (_price.length==2){
            currency = _price[0];
            amount = _price[1];
        }

        return FareDetail.builder()
                    .currency(currency)
                    .amount(amount)
                    .build();
    }
}
