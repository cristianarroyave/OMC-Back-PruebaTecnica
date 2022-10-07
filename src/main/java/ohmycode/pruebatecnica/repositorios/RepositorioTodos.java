package ohmycode.pruebatecnica.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ohmycode.pruebatecnica.entidades.Todo;

public interface RepositorioTodos extends JpaRepository<Todo, Integer> {
	
	List<Todo> findByTituloContaining(String titulo);
	
	@Query("SELECT t FROM Todo t LEFT JOIN Usuario u on t.usuario = u.id  WHERE u.username = ?1")
	List<Todo> encontrarByUsernameLike(String username);
}
