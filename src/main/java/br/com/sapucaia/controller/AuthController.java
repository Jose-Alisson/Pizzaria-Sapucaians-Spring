package br.com.sapucaia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sapucaia.repository.AuthRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private AuthRepository repository;

	@GetMapping("/findAll")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/findById")
	public ResponseEntity<?> getById(@RequestParam("id") Long id) {
		return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
	}

	@GetMapping("/findByEmail")
	public ResponseEntity<?> getByEmail(@RequestParam("email") String email) {
		return new ResponseEntity<>(repository.findByEmail(email), HttpStatus.OK);
	}
}
