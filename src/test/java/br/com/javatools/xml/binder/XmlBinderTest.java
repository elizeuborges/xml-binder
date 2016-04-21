package br.com.javatools.xml.binder;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class XmlBinderTest {

	private XmlBinder binder;
	
	@Before
	public void setUp() throws Exception {
		binder = new XmlBinder(getClass().getClassLoader().getResource("br/com/javatools/xml/binder/xml-test.xml"));
	}

	public static String umMetodoQualquer(){
		return "umValor";
	}
	
	@Test
	public void deveSetarSerPossivelInvocarMetodosApartirDoXml() {
		binder.bind("alias", this);
		String xml = binder.getXml();
		assertThat(xml, containsString("<Tag1>"+umMetodoQualquer()+"</Tag1>"));
		System.out.println(xml);
	}

}
