package com.ra.server.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ra.common.sample.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;

@Getter
@Setter
@ToString
@JacksonXmlRootElement(localName = "notebook")
public class KeepCollection {
    @JacksonXmlElementWrapper(localName = "ticket", useWrapping = false)
    public HashSet<Ticket> ticket = new HashSet<>();

}
