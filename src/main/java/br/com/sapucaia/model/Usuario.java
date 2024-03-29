package br.com.sapucaia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column( name = "contato")
	private String contato;
	
	@Column(name = "nome")
	private String name;
	
	@Column(name = "sobrenome")
	private String sobrenome;
	
	@ManyToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_id")
	private List<Endereco> enderecos;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Pedido> pedidos;
		
	@Column(name = "foto_url")
	private String fotoUrl;
}
