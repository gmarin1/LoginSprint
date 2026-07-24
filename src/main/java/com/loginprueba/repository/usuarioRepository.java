package com.loginprueba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loginprueba.model.Usuario;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

}
