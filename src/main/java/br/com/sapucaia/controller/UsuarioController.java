package br.com.sapucaia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/autoSign")
	public ResponseEntity<?> autoSign(@RequestBody Usuario usuario){
		Usuario user = repository.findByEmailAndProvider(usuario.getEmail(), usuario.getProvedorr());
		if(user == null) {
			return new ResponseEntity<>(repository.save(usuario), HttpStatus.OK);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
	}
	
	
	@DeleteMapping("/dltAll")
	public ResponseEntity<?> deleteAll(){
		repository.deleteAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
