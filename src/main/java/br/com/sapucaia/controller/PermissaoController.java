package br.com.sapucaia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sapucaia.model.Auth;
import br.com.sapucaia.model.Permissao;
import br.com.sapucaia.repository.AuthRepository;
import br.com.sapucaia.repository.PermissaoRepository;

@RestController
@RequestMapping("/api/permissao")
public class PermissaoController {

	@Autowired
	private PermissaoRepository repository;

	@Autowired
	private AuthRepository authRepository;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Permissao permissao) {
		return new ResponseEntity<>(repository.save(permissao), HttpStatus.OK);
	}

	@PostMapping("/set")
	public ResponseEntity<?> setPermissao(@RequestParam("email") String email, @RequestParam("permissonId") Long id) {
		Optional<Auth> auth_ = authRepository.findByEmail(email);

		if (auth_.isPresent()) {
			Optional<Permissao> permissao_ = repository.findById(id);
			if (permissao_.isPresent()) {
				auth_.get().getPermissoes().add(permissao_.get());
				authRepository.save(auth_.get());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Permissão Não Encontrada", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("Authenticador Não Encontrado", HttpStatus.NOT_FOUND);
		}
	}
}
