package br.com.sapucaia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Pedido;

@Service
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Query(value = "select p.* from pedidos p, usuarios u where p.usuario_id = u.id and u.id = ?1", nativeQuery = true)
	List<Pedido> findByUsuarioId(Long id);
}
