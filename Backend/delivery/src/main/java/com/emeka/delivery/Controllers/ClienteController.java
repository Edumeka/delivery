package com.emeka.delivery.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.DireccionDTO;
import com.emeka.delivery.DTO.ProductoDTO;
import com.emeka.delivery.DTO.TrabajoRealizadoDTO;
import com.emeka.delivery.DTO.UsuarioDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.DireccionService;
import com.emeka.delivery.Services.UsuarioService;
import com.emeka.delivery.models.TrabajoRealizado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/delivery/v1/clientes")
@CrossOrigin(origins = {
        "http://localhost:8000",
        "http://localhost:8080",
        "http://127.0.0.1:8000"
})
@Tag(name = "Cliente", description = "Controlador para gestionar la información de los clientes")

public class ClienteController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DireccionService direccionService;
    @Autowired
    private JwtGenerator jwtGenerator;
   @Operation(
    summary = "Crear una nueva dirección para un cliente",
    description = "Este endpoint permite crear una nueva dirección asociada a un cliente, proporcionando la información de la dirección en formato JSON.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Dirección creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta o datos inválidos proporcionados"),
        @ApiResponse(responseCode = "401", description = "No autorizado. Se requiere un token válido en el encabezado de autorización"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @PostMapping("/crearDireccionCliente")
    public ResponseEntity<String> crearDireccionCliente(@RequestHeader("Authorization") String token,
            @RequestBody DireccionDTO direccionDTO) {

        try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido o ausente"); // 401
                                                                                                         // Unauthorized
            }

            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);

            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);

            // Imprimir para verificar el correo extraído
            System.out.println("Correo extraído del token: " + correo);

            // Llamar al servicio para crear la dirección
            // Devolver una respuesta con éxito
            return ResponseEntity.ok(direccionService.crearDireccionCliente(direccionDTO, correo));

        } catch (ResponseStatusException ex) {
            // Manejo específico si no se encuentra al usuario
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con correo " + ex.getMessage() + " no encontrado");
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 con detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la dirección: " + e.getMessage());
        }
    }

    @Operation(
    summary = "Obtener todas las direcciones de un cliente",
    description = "Este endpoint permite obtener todas las direcciones asociadas a un cliente utilizando su token de autorización.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Direcciones obtenidas correctamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado. Se requiere un token válido en el encabezado de autorización"),
        @ApiResponse(responseCode = "404", description = "No se encontraron direcciones para el cliente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @GetMapping("/obtenerDireccionCliente")
    /**
     * Obtiene todas las direcciones de un cliente
     */
    public ResponseEntity<List<DireccionDTO>> obtenerDireccionesDelCliente(
            @RequestHeader("Authorization") String token) {
        try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
            }

            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);

            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);

            // Llamar al servicio para crear la dirección
            // Devolver una respuesta con éxito
            return ResponseEntity.ok(direccionService.obtenerDireccionesDelCliente(correo));

        } catch (ResponseStatusException ex) {
            // Manejo específico si no se encuentra al usuario
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 con detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @Operation(
    summary = "Crear un nuevo repartidor",
    description = "Este endpoint permite crear un nuevo repartidor utilizando un token de autorización. El repartidor es creado y registrado en el sistema.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Repartidor creado correctamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado. Se requiere un token válido en el encabezado de autorización"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta. Es posible que falten parámetros necesarios"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @GetMapping("/crearRepartidor")
    public ResponseEntity<String> crearRepartidor(@RequestHeader("Authorization") String token) {

        // Verificar que el token esté presente y comience con "Bearer"
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }

        // Eliminar el prefijo "Bearer " para obtener el token real
        token = token.substring(7);

        // Intentar extraer el correo desde el token
        String correo = jwtGenerator.getUsernameFromToken(token);

        return ResponseEntity.ok(usuarioService.crearRepartidor(correo));

    }

    @Operation(
    summary = "Obtener el tiempo estimado de espera",
    description = "Este endpoint permite obtener el tiempo estimado de espera para la entrega de un pedido utilizando un token de autorización.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Tiempo de espera obtenido correctamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado. Se requiere un token válido en el encabezado de autorización"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @GetMapping("/tiempoDeEspera")
    public ResponseEntity<String> tiempoDeEspera(@RequestHeader("Authorization") String token) {

        // Verificar que el token esté presente y comience con "Bearer"
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }

        // Eliminar el prefijo "Bearer " para obtener el token real
        token = token.substring(7);

        // Intentar extraer el correo desde el token
        String correo = jwtGenerator.getUsernameFromToken(token);

        return ResponseEntity.ok(usuarioService.tiempoDeEspera(correo));

    }

    @Operation(
    summary = "Verificar si el usuario tiene privilegios de administrador",
    description = "Este endpoint verifica si el usuario autenticado tiene privilegios de administrador utilizando el token de autorización proporcionado.",
    responses = {
        @ApiResponse(responseCode = "200", description = "El usuario tiene privilegios de administrador"),
        @ApiResponse(responseCode = "401", description = "No autorizado. Se requiere un token válido en el encabezado de autorización"),
        @ApiResponse(responseCode = "403", description = "El usuario no tiene privilegios de administrador"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @GetMapping("/esAdmin")
    public ResponseEntity<String> esAdmin(@RequestHeader("Authorization") String token) {

        // Verificar que el token esté presente y comience con "Bearer"
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }

        // Eliminar el prefijo "Bearer " para obtener el token real
        token = token.substring(7);

        // Intentar extraer el correo desde el token
        String correo = jwtGenerator.getUsernameFromToken(token);
       
        return ResponseEntity.ok(usuarioService.esAdmin(correo));

    }
    @Operation(
    summary = "Obtener la lista de todos los usuarios",
    description = "Este endpoint permite obtener la lista completa de usuarios registrados en el sistema.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @GetMapping("/obtenerUsuarios")
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @Operation(
    summary = "Eliminar un usuario por su ID",
    description = "Este endpoint permite eliminar un usuario específico del sistema usando su identificador único (ID).",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID proporcionado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @DeleteMapping("/eliminarUsuario/{idUsuario}")
    public String eliminarUsuario(@PathVariable int idUsuario) {
        return usuarioService.eliminarUsuario(idUsuario);
    }
    
    @Operation(
    summary = "Editar un usuario existente",
    description = "Este endpoint permite actualizar la información de un usuario en el sistema usando los datos proporcionados en el cuerpo de la solicitud.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, los datos del usuario no son válidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID proporcionado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    @PostMapping("/editarUsuario")
    public String editarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.editarUsuario(usuarioDTO);
    }
    

    @GetMapping("/obtenerRepartidores")
    public List<UsuarioDTO> obtenerRepartidores() {
        return usuarioService.obtenerRepartidores();
    }

    @GetMapping("/buscarHistorialRepartidor/{idUsuario}")
    public List<TrabajoRealizadoDTO> historialRepartidor(@PathVariable int idUsuario) {
        return usuarioService.historialRepartidor(idUsuario);
    }
    
    @GetMapping("/usuariosActivos")
    public int obtenerUsuariosActivos() {
        return usuarioService.usuariosActivos();
    }

    @GetMapping("/obtenerClientes")
    public List<UsuarioDTO> obtenerClientes() {
        return usuarioService.obtenerClientes();
    }
    
    
    
    
    @GetMapping("/historialCliente/{idUsuario}")
    public List<ProductoDTO> historialCliente(@PathVariable int idUsuario) {
        return usuarioService.historialCliente(idUsuario);
    }
    
}
