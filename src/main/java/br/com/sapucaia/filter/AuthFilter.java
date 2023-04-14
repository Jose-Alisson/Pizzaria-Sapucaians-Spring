package br.com.sapucaia.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.sapucaia.detail.UserDetailAuth;
import br.com.sapucaia.jwt.TokenService;
import br.com.sapucaia.repository.AuthRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = "";

		var autho = request.getHeader("Authorization");

		if (autho != null) {
			token = autho.replaceAll("Bearer ", "");
			var subject = tokenService.getSubject(token);
			var auth = new UserDetailAuth(repository.findByEmail(subject));

			var authentication = new UsernamePasswordAuthenticationToken(auth.getUsername(), null,
					auth.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

}