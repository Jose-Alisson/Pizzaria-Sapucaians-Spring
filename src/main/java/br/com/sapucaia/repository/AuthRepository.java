package br.com.sapucaia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sapucaia.model.Auth;

@Service
public interface AuthRepository extends JpaRepository<Auth, Long>{
	
	@Transactional
	@Query(value = "select u.* from auth u where u.email = ?1", nativeQuery = true)
	Optional<Auth> findByEmail(String email);
	
	@Query(value = "select exists(select 1 from auth a where a.email = ?1)", nativeQuery = true)
	long existByEmail(String email);
}
