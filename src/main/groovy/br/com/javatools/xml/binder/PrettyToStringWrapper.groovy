package br.com.javatools.xml.binder

class PrettyToStringWrapper {
	
	def static wrapper(wrapper, configuracao){
		if(wrapper instanceof Date)
			return new DateWrapper(when: wrapper, defaultDataPattern: configuracao.defaultDataPattern)
		else
			return wrapper
	}
	
}
