package ohmycode.pruebatecnica.servicios;

import java.util.List;

import ohmycode.pruebatecnica.dto.TodoDTO;
import ohmycode.pruebatecnica.entidades.Todo;
import ohmycode.pruebatecnica.excepciones.TodoException;

public interface ServicioTodos {

	Todo createTodo(TodoDTO todo) throws TodoException;
	
	Todo modifyTodo(TodoDTO todo, Integer id, String token) throws TodoException;
	
	Todo deleteTodo(Integer id) throws TodoException;
	
	List<Todo> searchTodoSorted(Integer numPag, Integer pagLen, String sortBy, String orderDir) throws TodoException;

}