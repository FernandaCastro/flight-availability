## Flight Availability

This is a REST/JSON Service, built upon Spring-Web. It retrieves flights availability data from an REST/XML Airline Mock Service. 

The availability results are first retrieved in XML format, and then converted into a different JSON model.

It implements a server component that receives REST/JSON request with criteria parameters, converts it into a REST/XML request to a mock service and then converts the result into a different JSON response.

#### The API (/flight-availability)

Request params:

    - origin (origin airport)
    - destination (destination airport)
    - departureDate 
    - returnDate
    - passengers (number of passengers)


Response:

It returns the list of flights available (returned by the airline web service) in the JSON
format:

````
{ "availability": [{
        "flight": {
            "operator" : "UA",
            "flightNumber" : "UA304",
            "departsFrom" : "MUC",
            "arrivesAt" : "IST",
            "departsOn" : {"date": "01-01-2014", "time": "08:52AM"},
            "arrivesOn" : {"date": "01-01-2014", "time": "12:49PM"},
            "flightTime" : "03:57",
            "farePrices" : {
            "first" : {
                "ticket" : { "currency" : "USD", "amount" : 472.00},
                "bookingFee" : { "currency" : "USD", "amount" : 29.50},
                "tax" : { "currency" : "USD", "amount" : 23.60}
            },
            "business" : {
                "ticket" : { "currency" : "USD", "amount" : 236.00},
                "bookingFee" : { "currency" : "USD", "amount" : 29.50},
                "tax" : { "currency" : "USD", "amount" : 23.60}
            },
            "economy" : {
                "ticket" : { "currency" : "USD", "amount" : 118.00},
                "bookingFee" : { "currency" : "USD", "amount" : 29.50},
                "tax" : { "currency" : "USD", "amount" : 23.60}
            }
        }
    }]
}
````

#### Mock XML API Service

Endpoint:
```
 private-anon-f93a4a8879-mockairline.apiary-mock.com/flights/<origin>/<destination>/<departureDate>/<returnDate>/<passenger>
```


Sample of the XML Retrieved:
```
<Availability>
  <Flight>
    <CarrierCode>EI</CarrierCode>
    <FlightDesignator>EI554</FlightDesignator>
    <OriginAirport>IST</OriginAirport>
    <DestinationAirport>DUB</DestinationAirport>
    <DepartureDate>2014-01-02T10:48:00.000Z</DepartureDate>
    <ArrivalDate>2014-01-02T13:04:00.000Z</ArrivalDate>
    <Fares>
      <Fare class="FIF">
        <BasePrice>EUR 272.00</BasePrice>
        <Fees>EUR 17.00</Fees>
        <Tax>EUR 13.60</Tax>
      </Fare>
      <Fare class="CIF">
        <BasePrice>EUR 136.00</BasePrice>
        <Fees>EUR 17.00</Fees>
        <Tax>EUR 13.60</Tax>
      </Fare>
      <Fare class="YIF">
        <BasePrice>EUR 68.00</BasePrice>
        <Fees>EUR 17.00</Fees>
        <Tax>EUR 13.60</Tax>
      </Fare>
    </Fares>
  </Flight>
  <Flight>
    <CarrierCode>EI</CarrierCode>
    <FlightDesignator>EI520</FlightDesignator>
    <OriginAirport>BOS</OriginAirport>
    <DestinationAirport>DUB</DestinationAirport>
    <DepartureDate>2014-01-05T02:03:00.000Z</DepartureDate>
    <ArrivalDate>2014-01-05T07:15:00.000Z</ArrivalDate>
    <Fares>
      <Fare class="FIF">
        <BasePrice>EUR 1248.00</BasePrice>
        <Fees>EUR 78.00</Fees>
        <Tax>EUR 62.40</Tax>
      </Fare>
      <Fare class="CIF">
        <BasePrice>EUR 624.00</BasePrice>
        <Fees>EUR 78.00</Fees>
        <Tax>EUR 62.40</Tax>
      </Fare>
      <Fare class="YIF">
        <BasePrice>EUR 312.00</BasePrice>
        <Fees>EUR 78.00</Fees>
        <Tax>EUR 62.40</Tax>
      </Fare>
    </Fares>
  </Flight>
</Availability>
```

### Tests 
* AirlineWebClientIntegrationTest
* FlightControllerTest
* FlightServiceTest
