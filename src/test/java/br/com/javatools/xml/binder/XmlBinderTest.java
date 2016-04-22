package br.com.javatools.xml.binder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class XmlBinderTest {

	private String xml = ""
			+"<RootTag>"
			+"	<Tag1>${alias.umMetodoQualquer()}</Tag1>"
			+"	<Tag1>${(new Date() + 1).format('dd-MM-yyyy')}</Tag1>"
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

}
