package br.com.javatools.xml.binder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

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
	public void deveSerPossivelFazerOperacoesComDatasNoXml() throws Exception {
		String xml = "<Data>${data + 1}</Data>";
		XmlBinder binder = new XmlBinder(xml);
		LocalDate data = LocalDate.now();
		binder.bind("data", toDate(data));
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(toDate(data.plusDays(1)));
		assertThat(binder.getXml(), is("<Data>"+dataFormatada+"</Data>"));
	}

	private Date toDate(LocalDate data) {
		return Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	@Test
	public void deveFormatarDatasPorPadrao() throws Exception {
		String xml = "<Data>${data}</Data>";
		XmlBinder binder = new XmlBinder(xml);
		Date data = new Date();
		binder.bind("data", data);
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(data);
		assertThat(binder.getXml(), is("<Data>"+dataFormatada+"</Data>"));
	}
	
	@Test
	public void devePermitirConfigurarOutroFormatoDeData() throws Exception {
		String xml = "<Data>${data}</Data>";
		XmlBinder binder = new XmlBinder(xml);
		Date data = new Date();
		String formatoDasDatas = "dd-MM";
		binder.getConfiguracoes().setDefaultDataPattern(formatoDasDatas);
		binder.bind("data", data);
		
		String dataFormatada = new SimpleDateFormat(formatoDasDatas).format(data);
		assertThat(binder.getXml(), is("<Data>"+dataFormatada+"</Data>"));
	}
	
	@Test
	public void deveSerPossivelIterarPorLista() throws Exception {
		XmlBinder binder = new XmlBinder(XmlBinderTest.class.getClassLoader().getResource("br/com/javatools/xml/binder/xml-list-test.xml"));
		binder.bind("lista", Arrays.asList("Valor1", "Valor2"));
		xmlMontado = binder.getXml();
		assertThat(xmlMontado, containsString("<Tag1>Valor1</Tag1>"));
		assertThat(xmlMontado, containsString("<Tag1>Valor2</Tag1>"));
	}
	
	@Test
	public void deveSerPossivelInvocarMetodosEstaticos() throws Exception {
		String xml = ""
				+ "<% import static br.com.javatools.xml.binder.XmlBinderTest.umMetodoQualquer %>"
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
