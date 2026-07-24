package com.loginprueba.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RegistroLogin")
public class RegistroLogin {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombre_completo;

    @Column(name = "fecha_login", nullable = false)
    private LocalDateTime fechaLogin;
    
    @Column(name = "email", nullable = false)
    private String email;

    public RegistroLogin() {}

    public RegistroLogin(String nombre_completo, String email) {
        this.nombre_completo = nombre_completo;
        this.fechaLogin = LocalDateTime.now();
        this.email = email;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public LocalDateTime getFechaLogin() {
		return fechaLogin;
	}

	public void setFechaLogin(LocalDateTime fechaLogin) {
		this.fechaLogin = fechaLogin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}  
    

}
