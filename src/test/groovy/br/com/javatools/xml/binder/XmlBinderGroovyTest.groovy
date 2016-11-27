package br.com.javatools.xml.binder

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test

class XmlBinderGroovyTest {

	XmlBinder binder;
	
	@Test
	void 'deve setar unica propriedade'() {
		binder = new XmlBinder(
			'''<RootTag>
					<Tag1>$propriedade1</Tag1>
				</RootTag>''')
		
		String propriedade1 = "valor"
		binder.bind "propriedade1", propriedade1
		String xml = binder.getXml()
		assertThat(xml, containsString("<Tag1>valor</Tag1>"))
	}

	@Test
	void 'deve ser possivel invocar metodos à partir do xml'() {
		
		def classe = [
				umMetodoQualquer: { return "um valor" }
			]
		
		binder = new XmlBinder(
			'''<RootTag>
					<Tag1>${alias.umMetodoQualquer()}</Tag1>
				</RootTag>''')
		
		binder.bind "alias", classe
		String xml = binder.getXml()
		assertThat(xml, containsString("<Tag1>um valor</Tag1>"))
	}
	
	@Test
	void 'deve setar propriedade com alias'() {
		binder = new XmlBinder(
			'''<RootTag>
					<Tag1>${alias.field1}</Tag1>
			   </RootTag>''')
				
		def obj = new OutraClasse()
		obj.field1 = "fasfadaswe"
		binder.bind "alias", obj
		String xml = binder.getXml()
		assertThat(xml, containsString("<Tag1>fasfadaswe</Tag1>"))
	}
	
	@Test
	void 'deve setar valores conforme field do objeto'() {
		binder = new XmlBinder(
			'''<RootTag>
					<Field1>${field1}</Field1>
					<Field2>${field2}</Field2>
					<Field3>${outro.field1}</Field3>
				</RootTag>'''
			)
		
		binder.bind(new ObjetoQualquer())
		String xml = binder.getXml()
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
