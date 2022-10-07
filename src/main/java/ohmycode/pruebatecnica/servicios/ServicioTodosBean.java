package ohmycode.pruebatecnica.servicios;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ohmycode.pruebatecnica.dto.TodoDTO;
import ohmycode.pruebatecnica.entidades.Todo;
import ohmycode.pruebatecnica.entidades.Usuario;
import ohmycode.pruebatecnica.excepciones.DatosError;
import ohmycode.pruebatecnica.excepciones.ErroresDeServicio;
import ohmycode.pruebatecnica.excepciones.TodoException;
import ohmycode.pruebatecnica.repositorios.RepositorioTodos;
import ohmycode.pruebatecnica.repositorios.RepositorioUsuarios;
import ohmycode.pruebatecnica.utils.Diccionarios;
import ohmycode.pruebatecnica.utils.JwtUtil;

@Service
public class ServicioTodosBean implements ServicioTodos {
	
	@Autowired
	private Diccionarios diccionarios;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RepositorioTodos repoTodos;
	
	@Autowired
	private RepositorioUsuarios repoUsuarios;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	/*
	 * Service to create a ToDo, if the user asigned does not exists
	 * an exception will be thrown.
	 */	
	@Override
	public Todo createTodo(TodoDTO todo) throws TodoException
	{
		Todo todoEntity = todoDtoToEntity(todo);
		Optional<Usuario> usuarioOptional = repoUsuarios.findById(todo.getUsuario());
		if(usuarioOptional.isEmpty())
		{
			throw new TodoException(new DatosError<>(ErroresDeServicio.USUARIO_NO_EXISTE, "El usuario no existe", todo.getUsuario()));
		}
		
		todoEntity.setUsuario(usuarioOptional.get());
		return repoTodos.save(todoEntity);
	}
	
	/*
	 * Service to modify a ToDo, this method controls that the ToDo
	 * exists and the user is allowed to modify, owners can modify their own ToDos
	 */	
	@Override
	public Todo modifyTodo(TodoDTO todo, Integer id, String token) throws TodoException
	{
		Optional<Todo> todoOptional = repoTodos.findById(id);
		if(todoOptional.isEmpty())
		{
			throw new TodoException(new DatosError<>(ErroresDeServicio.TODO_NO_EXISTE, "El todo no existe", id));
		}
		Todo todoEntity = todoOptional.get();
		if (!canModifyTodo(todoEntity, token.substring(7)))
		{
			throw new TodoException(new DatosError<>(ErroresDeServicio.PERMISO_DENEGADO, "No puedes modificar este todo", todoEntity));
		}
		if(todo.getUsuario() != null)
		{
			Optional<Usuario> usuarioOptional = repoUsuarios.findById(todo.getUsuario());
			if(usuarioOptional.isEmpty())
			{
				throw new TodoException(new DatosError<>(ErroresDeServicio.USUARIO_NO_EXISTE, "El usuario no existe", id));
			}
			todoEntity.setUsuario(usuarioOptional.get());
		}
		todoEntity.setTitulo(todo.getTitulo());
		todoEntity.setCompletado(todo.isCompletado());
		return repoTodos.save(todoEntity);
	}
	
	/*
	 * Service to delete a ToDo, if the ToDo does not exists
	 * an exception will be thrown
	 */	
	@Override
	public Todo deleteTodo(Integer id) throws TodoException{
		Optional<Todo> optionalTodo = repoTodos.findById(id);
		if(optionalTodo.isEmpty())
		{
			throw new TodoException(new DatosError<>(ErroresDeServicio.TODO_NO_EXISTE, "El todo no existe", id));
		}
		repoTodos.delete(optionalTodo.get());
		return optionalTodo.get();
	}

	/*
	 * Service to find Todo Sorted and Paginated, this method controls that the 
	 * parameters passed are real field in the columns of the database
	 * if some of them does not match an exception will be thrown
	 */	
	@Override
	public List<Todo> searchTodoSorted(Integer numPag, Integer pagLen, String sortBy, String orderDir) throws TodoException{
		if(sortBy.equals("id") || sortBy.equals("titulo") || sortBy.equals("usuario") || sortBy.equals("completado"))
		{
			if(orderDir.equals("asc") || orderDir.equals("desc"))
			{
				Page<Todo> todosPage = repoTodos.findAll(PageRequest.of(numPag, pagLen, Sort.by(diccionarios.getSort().get(orderDir), sortBy)));
				List<Todo> todos = todosPage.getContent();
				return todos;
			}
			throw new TodoException(new DatosError<>(ErroresDeServicio.PARAMETRO_NO_ESPERADO, "El parametro no es el esperado", orderDir));
		}
		throw new TodoException(new DatosError<>(ErroresDeServicio.PARAMETRO_NO_ESPERADO, "El parametro no es el esperado", sortBy));
	}
	
	/*
	 * Private method to map DTOs to Entities.
	 */	
	private Todo todoDtoToEntity(TodoDTO todo)
	{
		return modelMapper.map(todo, Todo.class);
	}
	
	/*
	 * Private method for validating that a user can modify a ToDo
	 */	
	private boolean canModifyTodo(Todo todo, String token)
	{
		String username = jwtUtil.extractUsername(token);
		Optional<Usuario> usuarioOptional = repoUsuarios.findByUsernameLike(username);
		return todo.getUsuario().equals(usuarioOptional.get());
	}

}
