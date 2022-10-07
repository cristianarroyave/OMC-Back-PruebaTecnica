package ohmycode.pruebatecnica.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ohmycode.pruebatecnica.entidades.Usuario;

public interface RepositorioUsuarios extends JpaRepository<Usuario, Integer>{

	Optional<Usuario> findByUsernameLike(String username);
}
