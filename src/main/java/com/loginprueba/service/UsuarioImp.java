package com.loginprueba.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginprueba.dto.LoginRequest;
import com.loginprueba.model.RegistroLogin;
import com.loginprueba.model.Usuario;
import com.loginprueba.repository.RegistroLoginRepository;
import com.loginprueba.repository.usuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioImp {
	
	@Autowired
	private usuarioRepository usuarioRepository;
	
	@Autowired
	private RegistroLoginRepository registroLoginRepository;
	
	@Transactional
	public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo " + usuario.getEmail() + " ya está registrado.");
        }
        usuario.setActivo(false);
        usuario.setFechaRegistro(LocalDateTime.now());
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioGuardado;
    }
	
	 @Transactional
	 public Map<String, String> login(LoginRequest request) {
	        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
	                .orElseThrow(() -> new RuntimeException("USUARIO_NO_ENCONTRADO"));
	        if (!usuario.getPassword().equals(request.getPassword())) {
	            throw new RuntimeException("PASSWORD_INCORRECTA");
	        }
	        	        
	        Map<String, String> tokens = new HashMap<>();
	        
	        usuario.setUltimoLogin(LocalDateTime.now());
	        usuario.setIntentosFallidos(0);
	        usuarioRepository.save(usuario);
	        
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String fechaStr = usuario.getUltimoLogin().format(formatter);
	        String idStr = String.valueOf(usuario.getId());
	        
	        tokens.put("nombre", usuario.getNombre_completo());
	        tokens.put("email", usuario.getEmail());
	        tokens.put("ultimoLogin", fechaStr);
	        tokens.put("id", idStr);
	        
	        RegistroLogin login = new RegistroLogin();
	        login.setEmail(usuario.getEmail());
	        login.setFechaLogin(usuario.getUltimoLogin());
	        login.setNombre_completo(usuario.getNombre_completo());
	        registroLoginRepository.save(login);
	        
	        return tokens;
	    }
	 
	 public void actualizarDatos(Long id, String passwordActual, String nuevaPassword,String nuevoNombre) {
	        Usuario usuario = usuarioRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	        
	        if (passwordActual != null) {
	        	if (!passwordActual.equals(usuario.getPassword()))
	        		throw new RuntimeException("La contraseña actual es incorrecta");
	        	else {
	        		
	        		if(nuevaPassword != null) {
	        			if (nuevaPassword.equals(usuario.getPassword()))
	 			           throw new RuntimeException("La nueva contraseña no puede ser igual a la anterior");
	        			else usuario.setPassword(nuevaPassword);
	        		}
	        		
	        		if(nuevoNombre != null) {
	    	        	if(nuevoNombre.equals(usuario.getNombre_completo()))
	    	        		throw new RuntimeException("El Nuevo Nombre no puede se igual al anterior");
	    	        	else usuario.setNombre_completo(nuevoNombre);
	    	        }
		        }
	        }else throw new RuntimeException("favor de ingresar la contraseña");

	        usuarioRepository.save(usuario);
	    }

}
