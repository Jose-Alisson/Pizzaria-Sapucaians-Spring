package br.com.sapucaia.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileController {
	
	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		 try {
		        // Obtenha o caminho da pasta onde os arquivos serão salvos
		        String uploadPath = "C:\\Users\\Jose Alisson\\Desktop\\Pizzaria\\local\\";
		        byte[] bytes = file.getBytes();
		        Path path = Paths.get(uploadPath + file.getOriginalFilename());
		        Files.write(path, bytes);
		        
		        return uploadPath + file.getOriginalFilename();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    return "";
	}
	
	
}
