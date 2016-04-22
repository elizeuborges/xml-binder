package br.com.javatools.xml.binder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.io.File;
import java.io.StringReader;

import org.junit.Test;

public class XmlBinderTest {

	private String xml = ""
			+"<RootTag>\n"
			+"\t<Tag1>${alias.umMetodoQualquer()}</Tag1>\n"
			+"\t<Tag1>${(new Date() + 1).format('dd-MM-yyyy')}</Tag1>\n"
			+"</RootTag>";
	
	public static String umMetodoQualquer(){
		return "umValor";
	}
	
	@Test
	public void deveSetarSerPossivelInvocarMetodosApartirDoXml() {
		XmlBinder binder = new XmlBinder(xml);
		binder.bind("alias", this);
		String xmlMontado = binder.getXml();
		assertThat(xmlMontado, containsString("<Tag1>"+umMetodoQualquer()+"</Tag1>"));
		System.out.println(xmlMontado);
	}
	
	@Test
	public void deveSerPossivelConstruirApartirDeReader() throws Exception {
		XmlBinder xmlBinder = new XmlBinder(new StringReader(xml));
		xmlBinder.bind("alias", this);
		String xmlMontado = xmlBinder.getXml();
		assertThat(xmlMontado, containsString("<Tag1>"+umMetodoQualquer()+"</Tag1>"));
	}
	
	@Test
	public void deveSerPossivelConstruirApartirDeURL() throws Exception {
		XmlBinder xmlBinder = new XmlBinder(XmlBinderTest.class.getClassLoader().getResource("br/com/javatools/xml/binder/xml-test.xml"));
		xmlBinder.bind("alias", this);
		String xmlMontado = xmlBinder.getXml();
		assertThat(xmlMontado, containsString("<Tag1>"+umMetodoQualquer()+"</Tag1>"));
	}
	
	@Test
	public void deveSerPossivelConstruirApartirArquivo() throws Exception {
		XmlBinder xmlBinder = new XmlBinder(
				new File(
						XmlBinderTest.class.getClassLoader().getResource("br/com/javatools/xml/binder/xml-test.xml").getFile()
						));
		xmlBinder.bind("alias", this);
		String xmlMontado = xmlBinder.getXml();
		assertThat(xmlMontado, containsString("<Tag1>"+umMetodoQualquer()+"</Tag1>"));
	}

}
