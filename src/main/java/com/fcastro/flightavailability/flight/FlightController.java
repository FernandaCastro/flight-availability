package com.fcastro.flightavailability.flight;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("flight-availability")
    public ResponseEntity<Map<String, List<Flight>>> listAvailability(@RequestParam String origin,
                                                                     @RequestParam String destination,
                                                                     @RequestParam String departureDate,
                                                                     @RequestParam String returnDate,
                                                                     @RequestParam Integer passengers) throws JsonProcessingException {

        var availability = flightService.getAvailableFlights(origin, destination, departureDate, returnDate, passengers);
        return ResponseEntity.ok(availability);

    }
}
