package br.com.sapucaia.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sapucaia.model.Produto;
import br.com.sapucaia.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin("*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Produto produto) {
		return new ResponseEntity<>(repository.save(produto), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Produto produto) {

		if (repository.existsById(id)) {
			Produto _prod = Produto.builder()
					.id(id)
					.fotoUrl(produto.getFotoUrl())
					.descricao(produto.getDescricao())
					.nomeDoProduto(produto.getNomeDoProduto())
					.preco(produto.getPreco())
					.categoria(produto.getCategoria())
					.emEstoque(produto.getEmEstoque())
					.build();
			return new ResponseEntity<>(repository.save(_prod),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		if(repository.existsById(id)){
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		Optional<Produto> prod = repository.findById(id);
		
		if(prod.isPresent()) {
			return new ResponseEntity<>(prod, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
	}
	
		
	@GetMapping("/imagem/{id}")
	public ResponseEntity<byte[]> downloadImagem(@PathVariable Long id) {
		Optional<Produto> _prod = repository.findById(id);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		if(_prod.isPresent()) {
			Produto prod = _prod.get();	
			if(!prod.getFotoUrl().equals("") && prod.getFotoUrl() != null) {
				File file = new File(prod.getFotoUrl());
				try {
					BufferedImage img = ImageIO.read(file);
					ImageIO.write(img, "png", outputStream);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		byte[] imagemBytes = outputStream.toByteArray();
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    headers.setContentLength(imagemBytes.length);
	    return new ResponseEntity<>(imagemBytes, headers, HttpStatus.OK);
	}
}
