package ohmycode.pruebatecnica.controlador;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ohmycode.pruebatecnica.dto.PaginationAndSortingDTO;
import ohmycode.pruebatecnica.dto.TodoDTO;
import ohmycode.pruebatecnica.entidades.Todo;
import ohmycode.pruebatecnica.excepciones.TodoException;
import ohmycode.pruebatecnica.repositorios.RepositorioTodos;
import ohmycode.pruebatecnica.seguridad.MyUserDetailsService;
import ohmycode.pruebatecnica.seguridad.auth.AuthenticationRequest;
import ohmycode.pruebatecnica.seguridad.auth.AuthenticationResponse;
import ohmycode.pruebatecnica.servicios.ServicioTodos;
import ohmycode.pruebatecnica.utils.JwtUtil;
import org.springframework.http.HttpHeaders;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Controlador {
	
	@Autowired
	private RepositorioTodos repoTodos;
	
	@Autowired
	private ServicioTodos srvTodos;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	/*
	 * Endpoint to log into the system, if the login is successful
	 * the return will be a JWT token
	 */	
	@PostMapping(
			path = "/login"
			)
	private ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest)
	{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
		}
		catch (AuthenticationException e)
		{
			return ResponseEntity.badRequest().body("Usuario o password incorrectos");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	/*
	 * Endpoint to get all the first 10 ToDos
	 * using the repository directly
	 */	
	@GetMapping(
			path = "/todos"
			)
	private ResponseEntity<?> getTodos()
	{
		Page<Todo> todosPage = repoTodos.findAll(PageRequest.of(0, 10));
		List<Todo> todos = todosPage.getContent();
		return ResponseEntity.ok(todos);
	}
	
	/*
	 * Endpoint that allows pagination to the ToDo list,
	 * first parameter is the page number, and the second parameter is 
	 * how many items are in each page.
	 * If the page length equals to 0 a bad request will be sent to the client
	 * to avoid printing the stack trace to the response
	 */	
	@GetMapping(
			path = "/todos/{numPag}/{pagLen}"
			)
	private ResponseEntity<?> getPaggingTodos(@PathVariable(name = "numPag") Integer numPag, @PathVariable(name = "pagLen") Integer pagLen)
	{
		if(pagLen == 0) 
		{
			return ResponseEntity.badRequest().build();
		}
		Page<Todo> todosPage = repoTodos.findAll(PageRequest.of(numPag, pagLen));
		List<Todo> todos = todosPage.getContent();
		return ResponseEntity.ok(todos);
	}
	
	/*
	 * Endpoint that allows pagination and also sorting,
	 * sortBy is the field that should be sorted
	 * orderDir is the sorting direction
	 */	
	@GetMapping(
			path = "/todos/{numPag}/{pagLen}/{sortBy}/{orderDir}"
			)
	private ResponseEntity<?> getPaggingTodosSorted(
				@PathVariable(name = "numPag") Integer numPag, 
				@PathVariable(name = "pagLen") Integer pagLen,
				@PathVariable(name = "sortBy") String sortBy,
				@PathVariable(name = "orderDir") String orderDir
			)
	{
		try {
			if(pagLen == 0)
			{
				return ResponseEntity.badRequest().build();
			}
			List<Todo> todos = srvTodos.searchTodoSorted(numPag, pagLen, sortBy, orderDir);
			return ResponseEntity.ok(todos);
		} catch (TodoException e) {
			return ResponseEntity.internalServerError().body(e.getDatos());
		}
	}
	
	/*
	 * Endpoint that returns a list of ToDos
	 * where the title  contains the text 
	 * passed as parameter
	 */	
	@GetMapping(
			path = "/todos/titulo/{title}"
			)
	private ResponseEntity<?> getTodoByTitle(@PathVariable(name = "title") String title)
	{
		List<Todo> todos = repoTodos.findByTituloContaining(title);
		return todos.size() > 0 ? ResponseEntity.ok(todos) : ResponseEntity.notFound().build();
	}
	
	/*
	 * Endpoint that returns a list of ToDos
	 * where the username matches the text 
	 * passed as parameter
	 */	
	@GetMapping(
			path = "/todos/username/{username}"
			)
	private ResponseEntity<?> getTodoByUsername(@PathVariable(name = "username") String username)
	{
		List<Todo> todos = repoTodos.encontrarByUsernameLike(username);
		return todos.size() > 0 ? ResponseEntity.ok(todos) : ResponseEntity.notFound().build();
	}
	
	/*
	 * Endpoint to create a todo
	 */	
	@PostMapping(
			path = "/todos"
			)
	private ResponseEntity<?> createTodo(@RequestBody TodoDTO todo)
	{
		try {
			Todo response = srvTodos.createTodo(todo);
			return ResponseEntity.ok(response);
		} catch (TodoException e) {
			return ResponseEntity.badRequest().body(e.getDatos());
		}
	}
	
	/*
	 * Endpoint to modify a todo, only available for authorized users
	 * via jwt token
	 */	
	@PutMapping(
			path = "/todos/{id}"
			)
	private ResponseEntity<?> modifyTodo(@RequestBody TodoDTO todo, @PathVariable(name = "id") Integer id, Principal principal)
	{
		try {
			Todo response = srvTodos.modifyTodo(todo, id, principal.getName());
			return ResponseEntity.ok(response);
		} catch (TodoException e) {
			return ResponseEntity.badRequest().body(e.getDatos());
		}
	}
	
	/*
	 * Endpoint to delete a todo, given the id passed as parameter
	 */	
	@DeleteMapping(
			path = "/todos/{id}"
			)
	private ResponseEntity<?> deleteTodo(@PathVariable(name = "id") Integer id)
	{
		try {
			Todo response = srvTodos.deleteTodo(id);
			return ResponseEntity.ok(response);
		} catch (TodoException e) {
			return ResponseEntity.internalServerError().body(e.getDatos());
		}
	}
	
	@PostMapping(
		path = "/pagAndSort"
	)
	private ResponseEntity<?> PagAndSort(@RequestBody PaginationAndSortingDTO pagAndSort)
	{
		try {
			if(pagAndSort == null)
			{
				return ResponseEntity.badRequest().build();
			}
			if(pagAndSort.getPagSize() == 0)
			{
				return ResponseEntity.badRequest().build();
			}
			List<Todo> todos = new ArrayList<Todo>();
			if(pagAndSort.getSortBy() == null || pagAndSort.getSortDir() == null)
			{
				todos = repoTodos.findAll(PageRequest.of(pagAndSort.getPagNum(), pagAndSort.getPagSize())).getContent();
			} 
			else 
			{
				todos = srvTodos.searchTodoSorted(pagAndSort.getPagNum(), pagAndSort.getPagSize(), pagAndSort.getSortBy(), pagAndSort.getSortDir());
			}
			return ResponseEntity.ok(todos);
		} catch (TodoException e) {
			return ResponseEntity.internalServerError().body(e.getDatos());
		}
	}
	
}
