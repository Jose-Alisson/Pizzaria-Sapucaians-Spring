package br.com.sapucaia.jwt;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.sapucaia.model.Auth;

@Service
public class TokenService {
	
	@Value("${chave.secreta.jwt.aplicacao}")
	private String chave;
	
	public String generateToken(Auth auth) {
		Date date = Date.from(LocalDateTime.now()
		        .plusMinutes(30)
		        .toInstant(ZoneOffset.of("-03:00")));
		
		return JWT.create()
				.withIssuer("sapucaians")
                .withSubject(auth.getEmail())
                .withClaim("id", auth.getId())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(chave));
	}
	
	public String getSubject(String token) {
		return JWT.require(Algorithm.HMAC256(chave))
                .withIssuer("sapucaians")
                .build().verify(token).getSubject();
	}
	
	//Gera token jwt para verificar um numero de telefone
	public String GenerateCodeVerify(String code) {
		Date date = Date.from(LocalDateTime.now()
		        .plusMinutes(3)
		        .toInstant(ZoneOffset.of("-03:00")));
		
		return JWT.create()
		.withIssuer("code verify")
		.withClaim("code", code)
		.withExpiresAt(date)
		.sign(Algorithm.HMAC256(chave));
	}
	
	public String CodeVerify(String token) {
		 return JWT.require(Algorithm.HMAC256(token))
				 .withIssuer("code verify")
				 .build()
				 .verify(token)
				 .getClaim("code").asString();
	}
}
