package br.com.sapucaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Pedido;

@Service
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
