package br.com.sapucaia.detail;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Auth;
import br.com.sapucaia.repository.AuthRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioDetailService implements UserDetailsService {

	@Autowired
	private AuthRepository repository;

	@Autowired
	private HttpSession httpSession;

	@Override
	public UserDetails loadUserByUsername(String email) {

		Optional<Auth> auth_ = 
				httpSession.getAttribute("auth") != null ?
						Optional.of((Auth) httpSession.getAttribute("auth")) 
						: repository.findByEmail(email);

		if (auth_.isEmpty()) {
			throw new UsernameNotFoundException("Não Foi Encontrado O Authenticador por E-mail: " + email);
		}
		
		return new UserDetailAuth(auth_);
	}
}
