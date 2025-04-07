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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://localhost:8080/"})
@RestController
@RequestMapping("/delivery/v1/auth")
@Tag(name = "AuthController", description = "Controlador para la autenticación de usuarios")        

public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Operation(
        summary = "Permite crear un nuevo registro para un cliente", 
        description = "Crea un cliente puede recibir un JSON con la información completa"
    )    
    @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        return userService.login(loginDto, response);
    }
    @Operation(
        summary = "Permite registrar un nuevo usuario",
        description = "Crea un nuevo usuario utilizando la información proporcionada en el JSON."
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        userService.register(registerDto);

        return new ResponseEntity<>("Usuario Registrado en el sistema!", HttpStatus.CREATED);
    }
    @Operation(
        summary = "Permite refrescar el token de autenticación",
        description = "Este endpoint permite a un usuario obtener un nuevo token de acceso utilizando el token de refresco proporcionado en la solicitud."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToke(Authentication authentication){

        String token = jwtGenerator.refreshToken(authentication);

        JwtResponseDTO jwtRefresh = new JwtResponseDTO(token);
        return new ResponseEntity<JwtResponseDTO>(jwtRefresh, HttpStatus.OK);
    }
    @Operation(
        summary = "Obtiene el usuario actualmente autenticado",
        description = "Este endpoint permite obtener los detalles del usuario que está actualmente autenticado en el sistema."
    )
    @GetMapping("/logued")
public ResponseEntity<UsuarioDTO> getLoguedUser(HttpServletRequest request) {
    return new ResponseEntity<>(userService.getLoguedUser(request), HttpStatus.OK);
}
    
@Operation(
    summary = "Cerrar sesión de un usuario",
    description = "Este endpoint permite cerrar la sesión de un usuario, invalidando su sesión actual y devolviendo una respuesta de éxito.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Sesión cerrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
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
