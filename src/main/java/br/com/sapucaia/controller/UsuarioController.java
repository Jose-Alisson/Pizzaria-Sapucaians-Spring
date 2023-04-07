package br.com.sapucaia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sapucaia.model.Usuario;
import br.com.sapucaia.repository.UsuarioRepository;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Usuario usuario) {
		return new ResponseEntity<>(repository.save(usuario), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id,@RequestBody Usuario user){
		
		if(repository.existsById(user.getId())) {
			Usuario user_ = Usuario.builder()
					.id(id)
					//.authPs(user.getAuthPs())
					.contato(user.getContato())
					.name(user.getName())
					.enderecos(user.getEnderecos())
					.pedidos(user.getPedidos())
					.fotoUrl(user.getFotoUrl())
					.build();
			return new ResponseEntity<>(repository.save(user_), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	} 
	
	@GetMapping("/findAll")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		Optional<Usuario> user = repository.findById(id);
		
		if(user.isPresent()) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
	
	
	@DeleteMapping("/dltAll")
	public ResponseEntity<?> deleteAll(){
		repository.deleteAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/dlt/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		if(repository.existsById(id)) {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
}
