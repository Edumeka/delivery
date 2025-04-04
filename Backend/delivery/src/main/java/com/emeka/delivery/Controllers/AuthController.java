package com.emeka.delivery.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.JwtResponseDTO;
import com.emeka.delivery.DTO.LoginDTO;
import com.emeka.delivery.DTO.RegisterDTO;
import com.emeka.delivery.DTO.UsuarioDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://127.0.0.1:8000/"})
@RestController
@RequestMapping("/delivery/v1/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtGenerator jwtGenerator;


    @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        return userService.login(loginDto, response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        userService.register(registerDto);

        return new ResponseEntity<>("Usuario Registrado en el sistema!", HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToke(Authentication authentication){

        String token = jwtGenerator.refreshToken(authentication);

        JwtResponseDTO jwtRefresh = new JwtResponseDTO(token);
        return new ResponseEntity<JwtResponseDTO>(jwtRefresh, HttpStatus.OK);
    }

    @GetMapping("/logued")
public ResponseEntity<UsuarioDTO> getLoguedUser(HttpServletRequest request) {
    return new ResponseEntity<>(userService.getLoguedUser(request), HttpStatus.OK);
}
    
@PostMapping("/logout")
public ResponseEntity<String> logout(HttpServletResponse response) {
    // Crear una cookie con el mismo nombre y valores predeterminados
    Cookie cookie = new Cookie("jwt", null); // El valor es null para eliminarla
    cookie.setHttpOnly(true); // Mantenerla como HttpOnly
    cookie.setSecure(true); // Usa true en producción si usas HTTPS
    cookie.setMaxAge(0); // Expira inmediatamente
    cookie.setPath("/"); // Asegúrate de usar el mismo path de la cookie original
    cookie.setAttribute("SameSite", "None"); // 🔥 Permite el uso en sitios cruzados
    // Añadir la cookie expirada a la respuesta
    response.addCookie(cookie);

    return ResponseEntity.ok("Sesión cerrada");
}



}
