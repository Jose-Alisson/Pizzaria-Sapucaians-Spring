package br.com.sapucaia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sapucaia.model.Pedido;
import br.com.sapucaia.repository.PedidoRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/pedido")
@CrossOrigin("*")
public class PedidoController {
	
	@Autowired
	private PedidoRepository repository;
	
	@Transactional
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Pedido pedido) {
		
		return new ResponseEntity<>(repository.save(pedido), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestParam("id") Long id, @RequestBody Pedido pedido) {

		Optional<Pedido> ped = repository.findById(id);

		if (ped.isPresent()) {
			Pedido _ped = Pedido.builder()
					.id(id)
					.numeroDoPedido(pedido.getNumeroDoPedido())
					.produtos(pedido.getProdutos())
					.descricao(pedido.getDescricao())
					.endereco(pedido.getEndereco())
					.tipoDePagamento(pedido.getTipoDePagamento())
					.troco(pedido.getTroco())
					.build();
			return new ResponseEntity<>(repository.save(_ped),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findById(@RequestParam("id") Long id){
		return new ResponseEntity<>(repository.findById(id),HttpStatus.OK);
	}
	
	@PostMapping("/findAll")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
	}
	
	
	@GetMapping("/print/{id}")
	public ResponseEntity<?> printRequest(@RequestParam("id") Long id){
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
