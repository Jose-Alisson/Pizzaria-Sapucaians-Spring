package br.com.sapucaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Produto;

@Service
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
