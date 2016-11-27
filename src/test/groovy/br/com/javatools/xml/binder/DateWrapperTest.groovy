package br.com.javatools.xml.binder

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

class DateWrapperTest {

	DateWrapper wrapper;
	
	def dataPattern = "dd/MM/yy"
	
	@Before
	void setUp() throws Exception {
		wrapper = new DateWrapper(when: new Date(), defaultDataPattern: dataPattern)
	}

	@Test
	void 'deve converter data conforme padrão informado'() {
		assertThat(wrapper.toString(), is(new Date().format(dataPattern)))
	}

	@Test
	void 'deve se possível somar datas'() {
		assertThat(wrapper.plus(1).toString(), is(new DateWrapper(when: new Date() + 1, defaultDataPattern: dataPattern).toString()))
	}

	@Test
	void 'deve ser possível subtrair datas'() {
		assertThat(wrapper.minus(1).toString(), is(new DateWrapper(when: new Date() - 1, defaultDataPattern: dataPattern).toString()))
	}

	@Test
	void 'deve retornar date wrapper em adicao'() {
		assertThat(wrapper.plus(1), is(DateWrapper.class))
	}

}
