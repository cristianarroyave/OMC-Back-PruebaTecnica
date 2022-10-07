package ohmycode.pruebatecnica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ohmycode.pruebatecnica.repositorios.RepositorioUsuarios;


@AutoConfigureMockMvc
@SpringBootTest
class ControllerTest {
	
	@MockBean
	private RepositorioUsuarios repoUsuarios;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getTodos() throws Exception {
		mockMvc.perform(get("/todos"))
			    .andExpect(status().isOk());
	}
	
	@Test
	void validParameters() throws Exception {
		mockMvc.perform(get("/todos/0/2/id/desc"))
	    .andExpect(status().isOk());
	}
	
	@Test
	void invalidParameters() throws Exception {
		mockMvc.perform(get("/todos/0/2/titulo/descee"))
	    .andExpect(status().is5xxServerError());
	}
	
	@Test
	void invalidParameters2() throws Exception {
		mockMvc.perform(get("/todos/0/2/edad/desc"))
	    .andExpect(status().is5xxServerError());
	}

}
