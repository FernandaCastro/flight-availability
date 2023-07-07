##Flight Availability

This is a REST/JSON Service built upon Spring-Web that requests flights availability info to an REST/XML Airline Mock Service. 

The availability results are retrieved in
XML format, and converted into a different JSON model.

It implements a server component that will receive REST/JSON requests and convert into
a REST/XML request to a mock service and then convert the results into a JSON
response.

####The API (/flight-availability)

These are the Request params:

    ? origin (origin airport)
    ? destination (destination airport)
    ? departureDate 
    ? returnDate
    ? passengers (number of passengers)

This is a sample of a Response:

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

### Tests 
* AirlineWebClientIntegrationTest
* FlightControllerTest
* FlightServiceTest
