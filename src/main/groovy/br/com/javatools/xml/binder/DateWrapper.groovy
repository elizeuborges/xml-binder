package br.com.javatools.xml.binder

import java.util.Date;

import groovy.lang.Delegate;

class DateWrapper {
	
	@Delegate
	private Date when;

	private String defaultDataPattern
	
	def String toString() {
		return when.format(defaultDataPattern)
	}
	
	def plus(value){
		new DateWrapper(when: (when + value), defaultDataPattern: defaultDataPattern)
	}

	def minus(value){
		new DateWrapper(when: (when - value), defaultDataPattern: defaultDataPattern)
	}
}
