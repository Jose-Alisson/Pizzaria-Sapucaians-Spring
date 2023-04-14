package br.com.sapucaia.jwt;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

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
	
	public String GenerateCodeVerify(String code) {
		Date date = Date.from(LocalDateTime.now()
		        .plusMinutes(30)
		        .toInstant(ZoneOffset.of("-03:00")));
		
		return JWT.create()
		.withIssuer("code verify")
		.withClaim("code", code)
		.withExpiresAt(date)
		.sign(Algorithm.HMAC256(chave));
	}
	
	public DecodedJWT getCodeVerify(String token) {
		 return JWT.require(Algorithm.HMAC256(chave))
				 .withIssuer("code verify")
				 .build()
				 .verify(token);
	}
}
