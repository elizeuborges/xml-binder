package br.com.javatools.xml.binder

import java.io.Writer;

import org.codehaus.groovy.runtime.InvokerHelper;

import groovy.text.SimpleTemplateEngine
import groovy.text.Template;
import groovy.util.logging.Slf4j;

class XmlBinder {

	private Map values = [:]

	private def xml
	
	Configuracoes configuracoes = new Configuracoes()
	
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
	
	/**
	 * Adiciona todos os valores dos campos deste objeto 
	 * que possua uma ou mais chave no xml com o nome 
	 * igual ao do respectivo campo
	 * <br><br>
	 * <b>Ex:</b><br>
	 * 
	 */
	def void bind(objeto){
		values.putAll(toMap(objeto))
	}

	def void bind(String key, Object valor){
		values.put key, formatIfNecessary(valor)
	}

	def String getXml() {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate xml
		setUp()
		template.make(values).toString()
	}

	def private setUp(){
		bind(configuracoes)
	}
	
	def private Map toMap(object) {
		object.class.declaredFields.findAll { !it.synthetic }.collectEntries {
			[ (it.name) : formatIfNecessary(object."$it.name") ]
		}
	}
	
	def private formatIfNecessary(object){
		if(object instanceof Date){
			return object.format(configuracoes.defaultDataPattern)
		} else {
			return object
		}
	}
	
}
