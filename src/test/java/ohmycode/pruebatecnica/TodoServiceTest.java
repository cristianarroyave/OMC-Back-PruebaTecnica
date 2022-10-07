package ohmycode.pruebatecnica;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ohmycode.pruebatecnica.dto.TodoDTO;
import ohmycode.pruebatecnica.entidades.Todo;
import ohmycode.pruebatecnica.entidades.Usuario;
import ohmycode.pruebatecnica.excepciones.TodoException;
import ohmycode.pruebatecnica.repositorios.RepositorioTodos;
import ohmycode.pruebatecnica.repositorios.RepositorioUsuarios;
import ohmycode.pruebatecnica.servicios.ServicioTodosBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TodoServiceTest {

    @InjectMocks
    private ServicioTodosBean srvTodos;

    @Mock
    private RepositorioTodos repoTodos;
    
    @Mock
	private ModelMapper modelMapper;
    
    @Mock 
    private RepositorioUsuarios repoUsuarios;
	
    @BeforeEach
    void setUp()
    {
		when(repoUsuarios.findById(any(Integer.class))).thenReturn(Optional.of(new Usuario(){{setId(1);}}));
        when(repoTodos.save(any(Todo.class))).thenReturn(new Todo(){{setId(1); setTitulo("Titulo  mock"); setCompletado(false);}});
    }
    
	@Test
	void deleteTodo() throws TodoException {
		srvTodos.deleteTodo(1);
	}

}
