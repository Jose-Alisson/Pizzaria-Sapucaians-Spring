package br.com.sapucaia.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.sapucaia.detail.UserDetailAuth;
import br.com.sapucaia.jwt.TokenService;
import br.com.sapucaia.message.MessagePs;
import br.com.sapucaia.message.MessageService;
import br.com.sapucaia.model.Auth;
import br.com.sapucaia.repository.AuthRepository;
import br.com.sapucaia.repository.PermissaoRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthRepository repository;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private MessageService messageService;

	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Auth auth) {
		auth.setPermissoes(permissaoRepository.findByType(auth.getTypeRule()));
		auth.setPassword(getPasswordEncoder().encode(auth.getPassword()));

		Auth auth_ = repository.save(auth);

		Map<String, Object> response = new HashMap<>();
		response.put("tokenAccess",  tokenService.generateToken(auth_));
		response.put("auth", auth_.getTypeRule());
	
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> autoSign(@RequestBody Auth auth_) {
		try {
			// automatic login
			if (auth_.getTokenAccess() != null) {
				var subject = tokenService.getSubject(auth_.getTokenAccess());
				var auth = new UserDetailAuth(repository.findByEmail(subject));
				var authentication = new UsernamePasswordAuthenticationToken(auth.getUsername(), null, auth.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);

				Map<String, Object> response = new HashMap<>();
				response.put("tokenAccess", tokenService.generateToken(Auth.builder().email((String) authentication.getPrincipal()).build()));
				//response.put("auth_type_rule", repository.findTypeByEmail(auth.getUsername()));
				response.put("auth", auth.getAuth().get());

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			
			// Login with E-mail and password
			if (repository.existByEmail(auth_.getEmail()) > 0) {
				var authToken = new UsernamePasswordAuthenticationToken(auth_.getEmail(), auth_.getPassword());
				Authentication authen = authenticationManager.authenticate(authToken);

				var auth = ((UserDetailAuth) authen.getPrincipal()).getAuth().get();
				
				Map<String, Object> response = new HashMap<>();
				response.put("tokenAccess",tokenService.generateToken(auth));
				//response.put("auth_type_rule", repository.findTypeByEmail(auth_.getEmail()));
				response.put("auth", auth);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token JWT já expirado");
		}
	}

	@GetMapping("/isExist")
	public ResponseEntity<?> isExist(@RequestParam("email") String email) {
		return new ResponseEntity<>(repository.existByEmail(email), HttpStatus.OK);
	}

	@GetMapping("verifyCode")
	public ResponseEntity<?> getCodeNumber(@RequestParam("number") String number,
			@RequestParam("provider") String provider) {

		int generatedCode = 10000 + new Random().nextInt(89999);
		MessagePs message = MessagePs.builder().to(number).message("Seu código de verificação é: " + generatedCode)
				.build();

		messageService.sendMessage(message, provider);
		
		Map<String, Object> response = new HashMap<>();
		response.put("token", tokenService.GenerateCodeVerify(generatedCode + ""));
	
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("verifyCode")
	public ResponseEntity<?> verifyCodeNumber(@RequestHeader("codeToken") String codeToken,
			@RequestParam("code") String code) {

		if (codeToken != null) {
			String token = codeToken.replaceAll("Bearer ", "");
			DecodedJWT decoded = tokenService.getCodeVerify(token);
			System.out.println("ésse é o codigo do token" + decoded.getClaim("code").asString());
			if (!(decoded.getClaim("code").asString().equals(code))) {
				return new ResponseEntity<>("Código Invalido", HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(code, HttpStatus.OK);
		}
		return new ResponseEntity<>("Token não present", HttpStatus.UNAUTHORIZED);
	}

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
