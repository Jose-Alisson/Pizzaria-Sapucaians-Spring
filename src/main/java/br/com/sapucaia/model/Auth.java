package br.com.sapucaia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.sapucaia.annotation.FieldOmit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auth {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL/*, fetch = FetchType.LAZY, mappedBy = "authPs"*/)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@FieldOmit(omit = true)
	@Column(name = "password")
	private String password;
	
	@FieldOmit(omit = true)
	@Transient
	private String tokenAccess;
	
	@Column(name = "type_rule")
	private String typeRule;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@OneToMany(mappedBy = "authPs", fetch = FetchType.EAGER)
	private List<Permissao> permissoes;
}
