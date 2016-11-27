package br.com.javatools.xml.binder

def i = [
		metodo: { parametro ->
			println "eu sou um metodo com ${parametro}"
		}
	]

i.metodo "um parametro"