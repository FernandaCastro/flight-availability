package com.fcastro.flightavailability.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fare {

    private String classname;

    private FareDetail ticket;

    private FareDetail bookingFee;

    private FareDetail tax;
}
