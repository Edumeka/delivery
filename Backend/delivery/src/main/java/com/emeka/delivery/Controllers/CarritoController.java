package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.CarritoDTO;
import com.emeka.delivery.DTO.CarritoRequest;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
@RequestMapping("/delivery/v1/carritos")
@CrossOrigin(origins = "http://localhost:8080/")
@Tag(name = "Carrito", description = "Operaciones relacionadas con el carrito de compras.")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;
 @Autowired
    private JwtGenerator jwtGenerator;
   
    @Operation(
        summary = "Guardar los productos en el carrito",
        description = "Este endpoint guarda el carrito de compras con los productos seleccionados por el usuario.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carrito guardado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
   @PostMapping("/guardarCarrito")
public ResponseEntity<String> guardarCarrito(
        @RequestHeader("Authorization") String token,
        @RequestBody CarritoRequest request) {

    try {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido o ausente");
        }

        token = token.substring(7);
        String correo = jwtGenerator.getUsernameFromToken(token);

        return ResponseEntity.ok(
                carritoService.guardarCarrito(request.getProducto(), correo, request.getCantidad()));

    } catch (ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario con correo " + ex.getMessage() + " no encontrado");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear el carrito: " + e.getMessage());
    }
}

@Operation(
    summary = "Ver los productos en el carrito de compras",
    description = "Este endpoint permite al usuario ver los productos que tiene en su carrito de compras utilizando el token de autorización.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Carrito de compras recuperado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarritoDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o expirado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
@GetMapping("/verCarrito")
public ResponseEntity<List<CarritoDTO>> verCarrito( @RequestHeader("Authorization") String token) {
    try {
        // Verificar que el token esté presente y comience con "Bearer"
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }
System.out.println("Token: " + token);
        // Eliminar el prefijo "Bearer " para obtener el token real
        token = token.substring(7);

        // Intentar extraer el correo desde el token
        String correo = jwtGenerator.getUsernameFromToken(token);           
      
       
  // Llamar al servicio para crear la dirección
        // Devolver una respuesta con éxito
        return ResponseEntity.ok(carritoService.verCarrito(correo));

    } catch (ResponseStatusException ex) {
        // Manejo específico si no se encuentra al usuario
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
        // En caso de error interno, devolver un estado 500 con detalles del error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

@Operation(
    summary = "Eliminar un carrito de compras",
    description = "Este endpoint permite eliminar un carrito de compras especificado por su ID.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Carrito de compras eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Carrito de compras no encontrado con el ID proporcionado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
@DeleteMapping("/eliminarCarrito/{idCarrito}")
public String eliminarCarrito(@PathVariable int idCarrito) {
    try {
        
        return carritoService.eliminarCarrito(idCarrito);
    } catch (ResponseStatusException ex) {
        return "Error: " + ex.getMessage();
    } catch (Exception e) {
        return "Error al eliminar el carrito: " + e.getMessage();
    }

        }

      
        
    
}
