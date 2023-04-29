package br.com.sapucaia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Produto;

@Service
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query(value = "select p.em_estoque from produtos p where p.id = ?1", nativeQuery = true)
	Optional<Integer> findStockById(Long id);
}
