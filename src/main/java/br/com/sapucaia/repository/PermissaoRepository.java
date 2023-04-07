package br.com.sapucaia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Permissao;

@Service
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
	
	@Query(value = "SELECT P.* FROM PERMISSOES P WHERE P.NAME = ?1", nativeQuery = true)
	Permissao findByName(String name);
	
	@Query(value = "select p.* from permissoes p where p.type_permission = ?1", nativeQuery = true)
	List<Permissao> findByType(String type);
}
