package br.com.sapucaia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enderecos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "endereco_id")
	private long id = 0;
	
	@Column(name = "nome_do_endereco")
	private String nomeDoEndereco;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "numero_da_casa")
	private String numeroDaCasa;
	
	@Column(name = "localidade")
	private String localidade;
}
