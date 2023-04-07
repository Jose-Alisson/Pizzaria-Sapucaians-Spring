package br.com.sapucaia.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.sapucaia.detail.UsuarioDetailService;
import br.com.sapucaia.model.Permissao;
import br.com.sapucaia.repository.PermissaoRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PermissaoFilter extends OncePerRequestFilter {

	@Autowired
	private UsuarioDetailService usuarioDetailService;
	
	@Autowired
	private PermissaoRepository permissaoRepository;

	  @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		  /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.isAuthenticated()) {
	            Object email = request.getSession().getAttribute("email");
	            if (email != null) {
	                UserDetails userDetails = usuarioDetailService.loadUserByUsername((String) email);
	                if (userDetails != null && userDetails.getUsername().equals(email)) {
	                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
	                    String path = request.getRequestURI().substring(request.getContextPath().length());
	                    String method = request.getMethod();

	                    boolean hasPermission = authorities.stream()
	                            .map(GrantedAuthority::getAuthority)
	                            .anyMatch(permissao -> {
	                                Permissao p = permissaoRepository.findByName(permissao);
	                                return p.getName().equals(method);
	                            });

	                    if (!hasPermission) {
	                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
	                        return;
	                    }
	                }
	            }
	        }*/

	        filterChain.doFilter(request, response);
	    }
}
