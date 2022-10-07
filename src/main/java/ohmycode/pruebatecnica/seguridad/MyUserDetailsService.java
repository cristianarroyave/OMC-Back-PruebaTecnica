package ohmycode.pruebatecnica.seguridad;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ohmycode.pruebatecnica.entidades.Usuario;
import ohmycode.pruebatecnica.repositorios.RepositorioUsuarios;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private RepositorioUsuarios repoUsuarios;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = repoUsuarios.findByUsernameLike(username);
		if(usuarioOptional.isEmpty())
		{
			throw new UsernameNotFoundException(username);
		}
		return new User(usuarioOptional.get().getUsername(), usuarioOptional.get().getPassword(), new ArrayList<>());
	}

}
