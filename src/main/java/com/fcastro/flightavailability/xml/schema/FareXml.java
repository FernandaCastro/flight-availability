package com.fcastro.flightavailability.xml.schema;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FareXml {

    @JacksonXmlProperty(localName = "class", isAttribute = true)
    private String classname;
    @JacksonXmlProperty(localName = "BasePrice")
    private String ticket;
    @JacksonXmlProperty(localName = "Fees")
    private String bookingFee;
    @JacksonXmlProperty(localName = "Tax")
    private String tax;
}
