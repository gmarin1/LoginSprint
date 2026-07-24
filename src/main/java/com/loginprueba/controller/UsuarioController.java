package com.loginprueba.controller;

import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginprueba.dto.LoginRequest;
import com.loginprueba.dto.UpdatePasswordRequest;
import com.loginprueba.model.Usuario;
import com.loginprueba.service.UsuarioImp;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioImp usuarioImp;
	
	@PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioImp.registrarUsuario(usuario);
            nuevoUsuario.setPassword(null);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
        	Map<String, String> tokens = usuarioImp.login(request);
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            String error = e.getMessage();

            return switch (error) {
                case "CUENTA_INACTIVA" ->
                        ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body("Tu cuenta aún no ha sido activada. Por favor, revisa tu correo electrónico.");

                case "USUARIO_NO_ENCONTRADO", "PASSWORD_INCORRECTA" ->
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Correo o contraseña incorrectos.");

                default ->
                        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Error al iniciar sesión: " + error);
            };
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado en el servidor.");
        }
    }
	
	@PostMapping("/update-password")
    public ResponseEntity<?> cambiarPasswordInterno(@RequestBody UpdatePasswordRequest request) {
        try {
            usuarioImp.actualizarDatos(request.getId(), request.getPasswordActual(), request.getNuevaPassword(), request.getNombre());
            //System.out.println(request.getId().toString()+ request.getPasswordActual().toString()+ request.getNuevaPassword().toString()+ request.getNombre().toString());
            return ResponseEntity.ok(Map.of("message", "datos actualizadados correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el cambio");
        }
    }
	
	@GetMapping("/healt")
	public String healt() {
		return LocalTime.now().toString();
	}

}
