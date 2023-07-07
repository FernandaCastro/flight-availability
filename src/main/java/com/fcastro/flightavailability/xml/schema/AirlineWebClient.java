package com.fcastro.flightavailability.xml.schema;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
public class AirlineWebClient {
    private static final String MOCK_AIRLINE_API = "private-anon-f93a4a8879-mockairline.apiary-mock.com/";
    private final WebClient webClient;

    public AirlineWebClient() {
        this.webClient = WebClient.builder().baseUrl(MOCK_AIRLINE_API).build();
    }

    public String getXmlAvailableFlights(String origin,
                                                  String destination,
                                                  String departureDate,
                                                  String returnDate,
                                                  Integer passengers) {
        //blocking way
        return webClient.get()
                .uri(String.format("/flights/%s/%s/%s/%s/%s", origin, destination, departureDate, returnDate, passengers))
                .retrieve()
                .onStatus(HttpStatus.Series.CLIENT_ERROR::equals, response -> Mono.just(new HttpClientErrorException(HttpStatus.BAD_GATEWAY)))
                .onStatus(HttpStatus.Series.SERVER_ERROR::equals, response -> Mono.just(new HttpClientErrorException(HttpStatus.BAD_GATEWAY)))
                .bodyToMono(String.class)
                .block(Duration.of(10000, ChronoUnit.MILLIS));
    }
}
