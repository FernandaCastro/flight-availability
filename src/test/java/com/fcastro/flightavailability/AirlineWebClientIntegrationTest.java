package com.fcastro.flightavailability;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fcastro.flightavailability.xml.schema.AirlineWebClient;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class AirlineWebClientIntegrationTest {

    @InjectMocks
    private AirlineWebClient mockAirlineWebClient;

    @Test
    public void givenGoodParams_whenGetRandomImage_returnOK() {
        //given
        String origin = "DUB";
        String destination = "DEL";
        String departureDate = "20151007";
        String returnDate = "20151020";
        int passengers = 2;

        //when
        var returnedXml = mockAirlineWebClient.getXmlAvailableFlights(origin, destination, departureDate, returnDate, passengers);

        //then
        assertThat(returnedXml).isNotNull();
    }
}
