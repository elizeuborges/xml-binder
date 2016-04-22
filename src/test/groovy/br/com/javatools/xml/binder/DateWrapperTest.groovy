package br.com.javatools.xml.binder

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

class DateWrapperTest {

	DateWrapper wrapper;
	def dataPattern = "dd/MM/yy"
	
	@Before
	public void setUp() throws Exception {
		wrapper = new DateWrapper(when: new Date(), defaultDataPattern: dataPattern)
	}

	@Test
	public void testToString() {
		assertThat wrapper.toString(), is(new Date().format(dataPattern))
	}

	@Test
	public void testPlus() {
		assertThat((wrapper + 1).toString(), is(new DateWrapper(when: new Date()+1, defaultDataPattern: dataPattern).toString()))
	}

	@Test
	public void testMinus() {
		assertThat((wrapper - 1).toString(), is(new DateWrapper(when: new Date()-1, defaultDataPattern: dataPattern).toString()))
	}

	@Test
	public void deveRetornarDateWrapperEmAdicao() {
		assertThat((wrapper + 1), is(DateWrapper.class))
	}
	

}
