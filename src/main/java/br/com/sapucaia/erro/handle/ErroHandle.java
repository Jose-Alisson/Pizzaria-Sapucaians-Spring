package br.com.sapucaia.erro.handle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;

@ControllerAdvice
public class ErroHandle {

	 @ExceptionHandler(UsernameNotFoundException.class)
	    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		 return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(TokenExpiredException.class)
	    public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException ex, HttpServletRequest request) {
	        HttpStatus status = HttpStatus.UNAUTHORIZED;
	        String message = "Token expirado : ";
	        return new ResponseEntity<>(message + ex.getMessage(), status);
	    }
}
