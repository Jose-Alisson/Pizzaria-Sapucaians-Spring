package br.com.sapucaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Usuario;

@Service
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query(value = "select * from usuarios u where u.email =?1 and u.provedor = ?2", nativeQuery = true)
	Usuario findByEmailAndProvider(String email, String provedor);
}
