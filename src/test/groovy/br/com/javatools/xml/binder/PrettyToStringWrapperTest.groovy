package br.com.javatools.xml.binder

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

class PrettyToStringWrapperTest {

	@Test
	public void deveEmpacotarDatas() {
		def data = PrettyToStringWrapper.wrapper new Date(), new Configuracoes()
		assertThat data, is(DateWrapper.class)
	}

	@Test
	public void naoDeveEmpacotarSeNaoForDatas() {
		def data = PrettyToStringWrapper.wrapper "oias", new Configuracoes()
		assertThat data, is(String.class)
	}

}
