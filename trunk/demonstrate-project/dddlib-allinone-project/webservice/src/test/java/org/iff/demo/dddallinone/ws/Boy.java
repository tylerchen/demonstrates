package org.iff.demo.dddallinone.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "boy")
//@XmlAccessorType(XmlAccessType.FIELD)
//@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
public class Boy {
	String name = "CY";
	int age = 10;
	List<String> list = new ArrayList<String>();
}