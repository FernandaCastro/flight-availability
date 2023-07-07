package com.fcastro.flightavailability;

import com.fcastro.flightavailability.flight.*;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Test
    public void givenValidParams_whenListAvailability_ReturnOK() throws Exception {
        //given
        String origin = "DUB";
        String destination = "DEL";
        String departureDate = "20151007";
        String returnDate = "20151020";
        int passengers = 2;

        List<Fare> fares = new ArrayList<>();
        fares.add(new Fare("FIF" ,new FareDetail("EUR", "272.00"), new FareDetail("EUR", "17.00"), new FareDetail("EUR", "13.60")));
        fares.add(new Fare("CIF" ,new FareDetail("EUR", "136.00"), new FareDetail("EUR", "17.00"), new FareDetail("EUR", "13.60")));
        fares.add(new Fare("YIF" ,new FareDetail("EUR", "68.00"), new FareDetail("EUR", "17.00"), new FareDetail("EUR", "13.60")));

        var availability = new ArrayList<Flight>();
        var flight = new Flight("EI", "EI554", "DUB", "DEL",
                OffsetDateTime.of(2014, 1, 2, 10, 48, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2014, 1, 2, 13, 4, 0, 0, ZoneOffset.UTC),
                null, fares);
        availability.add(flight);

        given(flightService.getAvailableFlights(origin, destination, departureDate, returnDate, passengers))
                .willReturn(Map.of("availability", availability));

        //when //then
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/flight-availability")
                .param("origin", origin)
                .param("destination", destination)
                .param("departureDate", departureDate)
                .param("returnDate", returnDate)
                .param("passengers", String.valueOf(passengers)))

                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String goodJSONResponse;
        try (var inputStream = FlightControllerTest.class.getClassLoader().getResourceAsStream("goodShortJsonResponse.json")) {
            goodJSONResponse = new BufferedReader(
                    new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining());
        }

        JSONAssert.assertEquals("{availability:[1]}", result, new ArraySizeComparator(JSONCompareMode.LENIENT));
        JSONAssert.assertEquals(goodJSONResponse, result, true);

    }
}
