package br.com.javatools.xml.binder

import java.lang.reflect.Field

class ObjectToMapConverter {

	def static Map<String, Object> toMap(object){
		object.class.declaredFields.findAll { !it.synthetic }.collectEntries {
			[ (it.name) : object."$it.name" ]
		}
	}
	
}
