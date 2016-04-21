package br.com.javatools.xml.binder

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test

import br.com.javatools.xml.binder.DocumentBinderTest.ObjetoQualquer;
import groovy.util.logging.Slf4j;;

@Slf4j
class DocumentBinderTest {

	XmlBinder binder;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	void deveSetarUnicaPropriedade() {
		binder = new XmlBinder(
			'''<RootTag>
					<Tag1>$propriedade1</Tag1>
				</RootTag>''')
		
		String propriedade1 = "valor"
		binder.bind "propriedade1", propriedade1
		String xml = binder.getXml()
		log.info xml
		assertThat(xml, containsString("<Tag1>${propriedade1}</Tag1>"))
	}

	private String umMetodoQualquer(){
		return "umValor"
	}
	
	@Test
	void deveSetarSerPossivelInvocarMetodosApartirDoXml() {
		binder = new XmlBinder(
			'''<RootTag>
					<Tag1>${alias.umMetodoQualquer()}</Tag1>
				</RootTag>''')
		
		binder.bind "alias", this
		String xml = binder.getXml()
		log.info xml
		assertThat(xml, containsString("<Tag1>${umMetodoQualquer()}</Tag1>"))
	}
	
	@Test
	void deveSetarPropriedadeComAlias() {
		binder = new XmlBinder(
			'''<RootTag>
					<Tag1>${alias.field1}</Tag1>
			   </RootTag>''')
				
		def obj = new OutraClasse()
		obj.field1 = "fasfadaswe"
		binder.bind "alias", obj
		String xml = binder.getXml()
		assertThat(xml, containsString("<Tag1>${obj.field1}</Tag1>"))
	}
	
	@Test
	void deveSetarValoresConformeFieldDoObjeto() {
		binder = new XmlBinder(
			'''<RootTag>
					<Field1>${field1}</Field1>
					<Field2>${field2}</Field2>
					<Field3>${outro.field1}</Field3>
				</RootTag>'''
			)
		
		binder.bind(new ObjetoQualquer())
		String xml = binder.getXml()
		log.info xml
		assertThat(xml, 
			allOf(
				containsString("<Field1>valorField1</Field1>"), 
				containsString("<Field2>valorField2</Field2>"),
				containsString("<Field3>valorDoField1</Field3>")
				)
			)
	}
	
	static class ObjetoQualquer {
		private String field1 = "valorField1"
		private String field2 = "valorField2"
		private OutraClasse outro = new OutraClasse();
	}
	
	static class OutraClasse {
		private String field1 = "valorDoField1"
	}

}
