package com.emeka.delivery.Services.imple;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.LoginDTO;
import com.emeka.delivery.DTO.RegisterDTO;
import com.emeka.delivery.DTO.UsuarioDTO;
import com.emeka.delivery.models.Usuario;
import com.emeka.delivery.Exceptions.ConflictException;
import com.emeka.delivery.models.Estado;
import com.emeka.delivery.models.Rol;
import com.emeka.delivery.Repositories.EstadoRepository;
import com.emeka.delivery.Repositories.RolRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import java.net.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@Service
public class UserServiceImple implements UserService {
    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private EstadoRepository estadoRepository;
  
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    
    @Override
    public UsuarioDTO register(RegisterDTO registerDto) {
        if (userRepository.existsByCorreo(registerDto.getCorreo())){
            throw new ConflictException("El usuario existe!");
        }
        Usuario user = new Usuario();
        user.setNombre(registerDto.getNombre());
        user.setApellido(registerDto.getApellido());
        user.setContrasenia(passwordEncoder.encode(registerDto.getContrasenia()));
        user.setCorreo(registerDto.getCorreo());
        user.setDni(registerDto.getDni());

        /**Se busca el rol de cliente para dejar al usuario como cliente predeterminado */
        Optional<Rol> optionalRol = rolRepository.findByRol("CLIENTE");

        //Instanciamos el rol
        // Si el rol existe, lo usamos; de lo contrario, creamos uno nuevo
        Rol rol;
        if (optionalRol.isPresent()) {
            rol = optionalRol.get(); // Si el rol existe, lo usamos
        } else {
            rol = new Rol();
            rol.setRol("CLIENTE");
            rol = rolRepository.save(rol); // Guardamos el nuevo rol en la base de datos
        }
        
        // Asignamos el rol al usuario
        user.setRol(rol);      
        
        /** Se busca el estado de cliente predeterminado */
Optional<Estado> opcionEstado = estadoRepository.findByEstado("ACTIVO");

// Instanciamos el estado
Estado estado;
if (opcionEstado.isPresent()) {
    // Si el estado ya existe, lo obtenemos de la base de datos
    estado = opcionEstado.get(); 
} else {
    // Si no existe el estado "ACTIVO", creamos uno nuevo
    estado = new Estado();
    estado.setEstado("ACTIVO");
    estado = estadoRepository.save(estado); // Guardamos el nuevo estado en la base de datos
}

// Asignamos el estado al usuario
user.setEstado(estado);
user.setUbicacionRepartidor(null); // Asignar null o una dirección predeterminada si es necesario     
       
        //user.setCreado(LocalDate.now());
        userRepository.save(user);

        UsuarioDTO userDto = new UsuarioDTO();
        userDto.setNombre(user.getNombre());
        userDto.setContrasenia(user.getContrasenia());
        userDto.setCorreo(user.getCorreo());
        userDto.setRol(user.getRol());
        return userDto;
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getCorreo(),
                            loginDto.getContrasenia()
                    )
            );
    
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
    
            // 🛑 Configurar la cookie JWT correctamente
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(false); // 🔒 Protege contra ataques XSS
            cookie.setSecure(true);  // ⚠️ Ponlo en `true` si usas HTTPS
            cookie.setPath("/");      // 📍 Disponible en toda la app
            cookie.setMaxAge(7 * 24 * 60 * 60); // ⏳ Expira en 7 días
            cookie.setAttribute("SameSite", "None"); // 🔥 Permite el uso en sitios cruzados
            

            response.addCookie(cookie); // 🔥 Agrega la cookie a la respuesta
    
            return ResponseEntity.ok().body("Inicio de Sesion exitoso");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

@Override
public UsuarioDTO getLoguedUser(HttpServletRequest request) {
    // 🔎 Buscar el token en las cookies
    String token = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        
        for (Cookie cookie : cookies) {
            if ("jwt".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
    }

    if (token == null) {
       // return null;
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
    }

    // 🔓 Extraer el correo desde el token
    String correo = jwtGenerator.getUsernameFromToken(token);

    // 🔍 Buscar al usuario en la base de datos
    Usuario user = userRepository.findByCorreo(correo)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    return modelMapper.map(user, UsuarioDTO.class);
}

}
