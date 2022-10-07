package ohmycode.pruebatecnica;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import ohmycode.pruebatecnica.controlador.Controlador;

@SpringBootTest
class PruebaTecnicaOmcApplicationTests {

	@Test
	void contextLoads() {
		Controlador controlador = new Controlador();
		assertNotNull(controlador);
	}

}
