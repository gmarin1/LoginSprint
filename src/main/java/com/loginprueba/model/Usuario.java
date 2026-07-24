package com.loginprueba.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "documento_identidad", unique = true, length = 20)
    private String documentoIdentidad;

    @Column(nullable = false)
    private String nombre_completo;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "intentos_fallidos")
    private int intentosFallidos = 0;

    // --- Auditoría ---
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "id_toku_customer")
    private String idTokuCustomer;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

	public Usuario() {
	}

	public Usuario(String nombre_completo, String email, String password) {
		this.nombre_completo = nombre_completo;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getIntentosFallidos() {
		return intentosFallidos;
	}

	public void setIntentosFallidos(int intentosFallidos) {
		this.intentosFallidos = intentosFallidos;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(LocalDateTime ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public String getIdTokuCustomer() {
		return idTokuCustomer;
	}

	public void setIdTokuCustomer(String idTokuCustomer) {
		this.idTokuCustomer = idTokuCustomer;
	}
    
    
    
    
	

}
