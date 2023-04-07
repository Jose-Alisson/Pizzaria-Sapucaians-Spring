package br.com.sapucaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.sapucaia.model.Usuario;

@Service
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
