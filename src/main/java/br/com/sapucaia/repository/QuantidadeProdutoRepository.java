package br.com.sapucaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.QuantidadeProduto;

@Service
public interface QuantidadeProdutoRepository extends JpaRepository<QuantidadeProduto, Long>{

}
