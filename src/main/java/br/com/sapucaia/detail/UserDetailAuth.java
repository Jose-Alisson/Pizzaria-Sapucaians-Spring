package br.com.sapucaia.detail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import br.com.sapucaia.model.Auth;
import br.com.sapucaia.model.Permissao;
import br.com.sapucaia.repository.PermissaoRepository;

public class UserDetailAuth implements UserDetails {

	/*@Autowired
	private PermissaoRepository permissaoRepository;*/
	
	private Optional<Auth> auth;

	public UserDetailAuth() {}

	public UserDetailAuth(Optional<Auth> auth) {
		this.auth = auth;
	}

	private static final long serialVersionUID = 1L;

	@Transactional
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		
		/*for (Permissao permissao : auth.get().getPermissoes()) {
			authorities.add(new SimpleGrantedAuthority(permissao.getName()));
		}*/
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return auth.orElse(new Auth()).getPassword();
	}

	@Override
	public String getUsername() {
		return auth.orElse(new Auth()).getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Optional<Auth> getAuth() {
		return auth;
	}

	public void setAuth(Optional<Auth> auth) {
		this.auth = auth;
	}
}
