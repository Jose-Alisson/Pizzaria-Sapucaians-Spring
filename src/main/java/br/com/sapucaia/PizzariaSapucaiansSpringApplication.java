package br.com.sapucaia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//exclude = SecurityAutoConfiguration.class
@SpringBootApplication()
public class PizzariaSapucaiansSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzariaSapucaiansSpringApplication.class, args);
	}

}
