package br.com.javatools.xml.binder

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

class PrettyToStringWrapperTest {

	@Test
	void 'deve empacotar datas'() {
		def data = PrettyToStringWrapper.wrapper new Date(), new Configuracoes()
		assertThat data, is(DateWrapper.class)
	}

	@Test
	void 'nao deve empacotar se não for datas'() {
		def data = PrettyToStringWrapper.wrapper "oias", new Configuracoes()
		assertThat data, is(String.class)
	}

}
