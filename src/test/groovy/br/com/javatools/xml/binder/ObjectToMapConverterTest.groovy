package br.com.javatools.xml.binder;

import static org.junit.Assert.*
import static org.hamcrest.Matchers.hasEntry

import org.junit.Before
import org.junit.Test

class ObjectToMapConverterTest {

	@Test
	void 'deve retornar field com o nome como chave'() {
		def map = ObjectToMapConverter.toMap(new ObjetoTeste())
		assertThat(map, hasEntry("fieldUm", "um"))
	}

	static class ObjetoTeste {
		String fieldUm = "um"
	}
	
}
