package br.com.javatools.xml.binder;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.StringReader;

import org.junit.After;
import org.junit.Test;

public class XmlBinderTest {

	private String xml = ""
			+"<RootTag>\n"
			+"\t<Tag1>${alias.umMetodoQualquer()}</Tag1>\n"
			+"\t<Tag1>${(new Date() + 1).format('dd-MM-yyyy')}</Tag1>\n"
			+"</RootTag>";
	
	private String xmlMontado;

	@After
	public void printarXmlMontado(){
		System.out.println(xmlMontado);
	}
	
	public static String umMetodoQualquer(){
		return "umValor";
	}
	
	@Test
	public void deveSerPossivelInvocarMetodosEstaticos() throws Exception {
		String xml = ""
				+ "<% import static br.com.javatools.xml.binder.XmlBinderTest.* %>"
				+"<RootTag>\n"
				+"\t<Tag1>${umMetodoQualquer()}</Tag1>\n"
				+"</RootTag>";
		
		bindAndTest(new XmlBinder(xml));
	}

	private void bindAndTest(XmlBinder binder) {
		binder.bind("alias", this);
		xmlMontado = binder.getXml();
		assertThat(xmlMontado, containsString("<Tag1>"+umMetodoQualquer()+"</Tag1>"));
	}
	
	@Test
	public void deveSetarSerPossivelInvocarMetodosApartirDoXml() {
		bindAndTest(new XmlBinder(xml));
	}
	
	@Test
	public void deveSerPossivelConstruirApartirDeReader() throws Exception {
		bindAndTest(new XmlBinder(new StringReader(xml)));
	}
	
	@Test
	public void deveSerPossivelConstruirApartirDeURL() throws Exception {
		bindAndTest(new XmlBinder(
						XmlBinderTest.class.getClassLoader().getResource("br/com/javatools/xml/binder/xml-test.xml")
				));
	}
	
	@Test
	public void deveSerPossivelConstruirApartirArquivo() throws Exception {
		bindAndTest(new XmlBinder(
						new File(
								XmlBinderTest.class.getClassLoader().getResource("br/com/javatools/xml/binder/xml-test.xml").getFile()
						)));
	}

}
