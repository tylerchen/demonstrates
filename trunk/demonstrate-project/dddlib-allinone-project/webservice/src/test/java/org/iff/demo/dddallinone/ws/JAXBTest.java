package org.iff.demo.dddallinone.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

public class JAXBTest {

	public static void main(String[] args) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshallXmlType(new Boy(), baos);
		System.out.println(baos.toString());
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		System.out.println(unmarshallXmlType(Boy.class,bais));
	}

	@SuppressWarnings("unchecked")
	public static <T> void marshallXmlType(T value, OutputStream os) {
		try {
			Class<T> clzz = (Class<T>) value.getClass();
			JAXBContext context = JAXBContext.newInstance(clzz);
			Marshaller m = context.createMarshaller();
			m.setProperty("jaxb.formatted.output", true);
			QName name = new QName(clzz.getSimpleName());
			JAXBElement<T> element = new JAXBElement<T>(name, clzz, value);
			m.marshal(element, os);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static <T> T unmarshallXmlType(Class<T> clzz, InputStream input) {
		try {
			JAXBContext context = JAXBContext.newInstance(clzz);
			Unmarshaller u = context.createUnmarshaller();
			JAXBElement<T> element = u.unmarshal(new StreamSource(input), clzz);
			return element.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main1(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Boy.class);

		Marshaller marshaller = context.createMarshaller();
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Boy boy = new Boy();
		marshaller.marshal(boy, System.out);
		System.out.println();

		String xml = "<boy><name>David</name><aaa dfda=\"add\">vasd</aaa></boy>";
		Boy boy2 = (Boy) unmarshaller.unmarshal(new StringReader(xml));
		System.out.println(boy2.name);
	}

	public static void main0(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Boy.class);

		Marshaller marshaller = context.createMarshaller();
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Boy boy = new Boy();
		marshaller.marshal(boy, System.out);
		System.out.println();

		String xml = "<boy><name>David</name><aaa dfda=\"add\">vasd</aaa></boy><boy><name>David</name><aaa dfda=\"add\">vasd</aaa></boy>";
		Boy boy2 = (Boy) unmarshaller.unmarshal(new StringReader(xml));
		System.out.println(boy2.name);
	}
}
