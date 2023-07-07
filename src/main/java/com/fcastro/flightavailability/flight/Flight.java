package com.fcastro.flightavailability.flight;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("flight")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT )
public class Flight {

    private String operator;

    private String flightNumber;

    private String departsFrom;

    private String arrivesAt;

    @JsonSerialize(using = ArriveDepartureSerializer.class)
    @JsonDeserialize(using = ArriveDepartureDeserializer.class)
    private OffsetDateTime departsOn;

    @JsonSerialize(using = ArriveDepartureSerializer.class)
    @JsonDeserialize(using = ArriveDepartureDeserializer.class)
    private OffsetDateTime arrivesOn;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime flightTime;

    @JsonSerialize(using = FareSerializer.class)
    @JsonDeserialize(using = FareDeserializer.class)
    private List<Fare> farePrices;

    public LocalTime getFlightTime(){
        var duration = Duration.between(departsOn, arrivesOn);
        return LocalTime.of((int)duration.toHours(), duration.toMinutesPart());
    }

}
