package br.com.sapucaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Endereco;

@Service
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

}
