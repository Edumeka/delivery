package com.emeka.delivery.Controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.models.Usuario;
import com.emeka.delivery.Repositories.UsuarioRepository;
@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://127.0.0.1:8000/"})
@RestController
public class VerifyTokenController {

    private final UsuarioRepository usuarioRepository;

    public VerifyTokenController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @RequestMapping("/admin")
    public String admin() {
        // Obtener la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Obtener el nombre de usuario (correo en este caso)
        String correo = authentication.getName();
        
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return "Hola bienvenido Admin, " + usuario.getNombre() + " " + usuario.getApellido() + "!";
    }

    @RequestMapping("/user")
    public String user() {
        // Obtener la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Obtener el nombre de usuario (correo en este caso)
        String correo = authentication.getName();
        
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return "Hola bienvenido User, " + usuario.getNombre() + " " + usuario.getContrasenia() + "!";
    }
}
