package com.fcastro.flightavailability;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fcastro.flightavailability.flight.Flight;
import com.fcastro.flightavailability.xml.schema.AirlineWebClient;
import com.fcastro.flightavailability.flight.FlightService;
import com.fcastro.flightavailability.xml.schema.FlightXmlMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlightServiceTest {

    @Mock
    AirlineWebClient airlineWebClient;

    @Spy
    FlightXmlMapper flightXmlMapper;

    @Spy
    XmlMapper xmlMapper;

    @InjectMocks
    FlightService flightService;

    private Map<String, List<Flight>> availability;

    @BeforeAll
    public void givenShortXMLResponse_whenGetAvailableFlights() throws IOException {
        airlineWebClient = mock(AirlineWebClient.class);
        flightXmlMapper = spy(FlightXmlMapper.class);
        xmlMapper = spy(XmlMapper.class);
        flightService = new FlightService(airlineWebClient, xmlMapper, flightXmlMapper);

        //given
        String goodXMLResponse;
        try (var xmlInputStream = FlightServiceTest.class.getClassLoader().getResourceAsStream("goodShortXmlResponse.xml")) {
            goodXMLResponse = new BufferedReader(
                    new InputStreamReader(xmlInputStream))
                    .lines()
                    .collect(Collectors.joining());
        }

        given(airlineWebClient.getXmlAvailableFlights(anyString(),anyString(), anyString(), anyString(), anyInt())).willReturn(goodXMLResponse);

        //when
        availability = flightService.getAvailableFlights("DUB", "DEL", "20151007", "20151020", 2);
    }

    @Test
    public void givenShortXMLResponse_whenGetAvailableFlights_returnTwoAvailableFlights() {

        //then
        Assertions.assertNotNull(availability);
        var flights = availability.get("availability");
        Assertions.assertNotNull(flights);
        Assertions.assertEquals(2, flights.size());
    }

    @Test
    public void givenShortXMLResponse_whenGetAvailableFlights_returnCorrectFirstFlightBaseInfo() {
        //then
        var flights = availability.get("availability");
        var flight = flights.get(0);

        Assertions.assertEquals("EI", flight.getOperator());
        Assertions.assertEquals("EI554", flight.getFlightNumber());
        Assertions.assertEquals("IST", flight.getDepartsFrom());
        Assertions.assertEquals("DUB", flight.getArrivesAt());


        Assertions.assertEquals("2014-01-02T10:48Z", flight.getDepartsOn().toString());
        Assertions.assertEquals("2014-01-02T13:04Z", flight.getArrivesOn().toString());
        Assertions.assertEquals("02:16", flight.getFlightTime().toString());
    }

    @Test
    public void givenShortXMLResponse_whenGetAvailableFlights_returnCorrectSecondFlightBaseInfo() {
        //then
        var flights = availability.get("availability");
        var flight = flights.get(1);

        Assertions.assertEquals("EI", flight.getOperator());
        Assertions.assertEquals("EI520", flight.getFlightNumber());
        Assertions.assertEquals("BOS", flight.getDepartsFrom());
        Assertions.assertEquals("DUB", flight.getArrivesAt());


        Assertions.assertEquals("2014-01-05T02:03Z", flight.getDepartsOn().toString());
        Assertions.assertEquals("2014-01-05T07:15Z", flight.getArrivesOn().toString());
        Assertions.assertEquals("05:12", flight.getFlightTime().toString());
    }

    @Test
    public void givenShortXMLResponse_whenGetAvailableFlights_returnCorrectFirstFlightFarePriceInfo() {
        //then
        var flights = availability.get("availability");
        var flight = flights.get(0);

        Assertions.assertNotNull(flight.getFarePrices());
        Assertions.assertEquals(3, flight.getFarePrices().size());

        var farePrice = flight.getFarePrices().get(0);
        Assertions.assertEquals("FIF", farePrice.getClassname());
        Assertions.assertEquals("EUR", farePrice.getTicket().getCurrency());
        Assertions.assertEquals("272.00", farePrice.getTicket().getAmount());
        Assertions.assertEquals("17.00", farePrice.getBookingFee().getAmount());
        Assertions.assertEquals("13.60", farePrice.getTax().getAmount());

        farePrice = flight.getFarePrices().get(1);
        Assertions.assertEquals("CIF", farePrice.getClassname());
        Assertions.assertEquals("136.00", farePrice.getTicket().getAmount());
        Assertions.assertEquals("17.00", farePrice.getBookingFee().getAmount());
        Assertions.assertEquals("13.60", farePrice.getTax().getAmount());

        farePrice = flight.getFarePrices().get(2);
        Assertions.assertEquals("YIF", farePrice.getClassname());
        Assertions.assertEquals("68.00", farePrice.getTicket().getAmount());
        Assertions.assertEquals("17.00", farePrice.getBookingFee().getAmount());
        Assertions.assertEquals("13.60", farePrice.getTax().getAmount());
    }
}
