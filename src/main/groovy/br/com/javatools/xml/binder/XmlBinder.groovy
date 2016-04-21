package br.com.javatools.xml.binder

import groovy.text.SimpleTemplateEngine
import groovy.text.Template;
import groovy.util.logging.Slf4j;

class XmlBinder {

	Map values = [:]

	def xml
	
	XmlBinder(String xml){
		this.xml = xml
	}
	
	XmlBinder(Reader reader){
		xml = reader
	}

	XmlBinder(File file){
		xml = file
	}
	
	XmlBinder(URL url){
		xml = url
	}
	
	def bind(objeto){
		values.putAll(toMap(objeto))
	}

	def bind(String key, Object valor){
		values.put key, valor
	}

	def String getXml() {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate xml
		template.make(values).toString()
	}

	def Map toMap(object) {
		object.class.declaredFields.findAll { !it.synthetic }.collectEntries {
			[ (it.name):object."$it.name" ]
		}
	}
}
