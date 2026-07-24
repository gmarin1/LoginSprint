package com.loginprueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginprueba.model.RegistroLogin;

public interface RegistroLoginRepository extends JpaRepository<RegistroLogin, Long>{

}
