package br.com.javatools.xml.binder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Test;

public class XmlBinderTest {

	public static String umMetodoQualquer(){
		return "umValor";
	}
	
	@Test
	public void deveSetarSerPossivelInvocarMetodosApartirDoXml() {
		URL xml = XmlBinderTest.class.getClassLoader().getResource("br/com/javatools/xml/binder/xml-test.xml");
		assertThat(xml, is(notNullValue()));
		XmlBinder binder = new XmlBinder(xml);
		binder.bind("alias", this);
		String xmlMontado = binder.getXml();
		assertThat(xmlMontado, containsString("<Tag1>"+umMetodoQualquer()+"</Tag1>"));
		System.out.println(xmlMontado);
	}

}
