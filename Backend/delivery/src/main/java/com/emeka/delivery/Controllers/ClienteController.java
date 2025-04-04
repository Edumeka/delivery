package com.emeka.delivery.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.DireccionDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.DireccionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping("/delivery/v1/clientes")
@CrossOrigin(origins = {
    "http://localhost:8000",
    "http://localhost:8080",
    "http://127.0.0.1:8000"
})

public class ClienteController {
    @Autowired
    private DireccionService direccionService;   
 @Autowired
    private JwtGenerator jwtGenerator;
  

    @PostMapping("/crearDireccionCliente")
public ResponseEntity<String> crearDireccionCliente(@RequestHeader("Authorization") String token, @RequestBody DireccionDTO direccionDTO) {

    try {
        // Verificar que el token esté presente y comience con "Bearer"
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido o ausente"); // 401 Unauthorized
        }

        // Eliminar el prefijo "Bearer " para obtener el token real
        token = token.substring(7);

        // Intentar extraer el correo desde el token
        String correo = jwtGenerator.getUsernameFromToken(token);
        
        // Imprimir para verificar el correo extraído
        System.out.println("Correo extraído del token: " + correo);

      
       
  // Llamar al servicio para crear la dirección
        // Devolver una respuesta con éxito
        return ResponseEntity.ok( direccionService.crearDireccionCliente(direccionDTO, correo));

    } catch (ResponseStatusException ex) {
        // Manejo específico si no se encuentra al usuario
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con correo " + ex.getMessage() + " no encontrado");
    } catch (Exception e) {
        // En caso de error interno, devolver un estado 500 con detalles del error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la dirección: " + e.getMessage());
    }
}


    
    @GetMapping("/obtenerDireccionCliente")
    /**
     * Obtiene todas las direcciones de un cliente
     */
    public ResponseEntity<List<DireccionDTO>> obtenerDireccionesDelCliente(@RequestHeader("Authorization") String token) {
      System.out.println("Token: "+token);
        try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
            }
    
            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);
    
            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);
            
            // Imprimir para verificar el correo extraído
            System.out.println("Correo extraído del token: " + correo);    
          
           
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
    
    
}
