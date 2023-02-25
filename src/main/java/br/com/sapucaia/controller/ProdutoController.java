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
import org.springframework.web.multipart.MultipartFile;

import br.com.sapucaia.model.Produto;
import br.com.sapucaia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produto")
@CrossOrigin("*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;
	
	private FileController fileController;
	
	public ProdutoController() {
		this.fileController = new FileController();
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Produto produto) {
		System.out.println(produto.toString());
		return new ResponseEntity<>(repository.save(produto), HttpStatus.OK);
	}
	
	@PostMapping("/save/withFoto")
	public ResponseEntity<?> save(@RequestParam("file") MultipartFile file) {
		//produto.setFoto(this.fileController.handleFileUpload(file));
		
		return new ResponseEntity<>(this.fileController.handleFileUpload(file) ,HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestParam("id") Long id, @RequestBody Produto produto) {

		Optional<Produto> prod = repository.findById(id);

		if (prod.isPresent()) {
			Produto _prod = Produto.builder()
					.id(id).foto(produto.getFoto())
					.descricao(produto.getDescricao())
					.nomeDoProduto(produto.getNomeDoProduto())
					.preco(produto.getPreco())
					.emEstoque(produto.getEmEstoque())
					.build();
			return new ResponseEntity<>(repository.save(_prod),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findById(@RequestParam("id") Long id){
		return new ResponseEntity<>(repository.findById(id),HttpStatus.OK);
	}
	
	@PostMapping("/findAll")
	public ResponseEntity<?> findAll(){
		System.out.println("foi");
		return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
	}
	
	@PostMapping("/imagem/{id}")
	public ResponseEntity<byte[]> downloadImagem(@PathVariable Long id) {
		Optional<Produto> _prod = repository.findById(id);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		if(_prod.isPresent()) {
			Produto prod = _prod.get();
			
			if(!prod.getFoto().equals("") && prod.getFoto() != null) {
				File file = new File(prod.getFoto());
				try {
					BufferedImage img = ImageIO.read(file);
					ImageIO.write(img, "png", outputStream);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			} else {
				File file = new File("C:\\Users\\Jose Alisson\\Desktop\\Pizzaria\\local\\notFound.png");
				try {
					BufferedImage img = ImageIO.read(file);
					ImageIO.write(img, "png", outputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		byte[] imagemBytes = outputStream.toByteArray();
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    headers.setContentLength(imagemBytes.length);
	    return new ResponseEntity<>(imagemBytes, headers, HttpStatus.OK);
	}
}
