package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.CarritoDTO;

import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.PedidoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/delivery/v1/pedidos")
@CrossOrigin(origins = "http://localhost:8000")
public class PedidoController {
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/guardarPedido")
    public ResponseEntity<String> guardarPedido(@RequestHeader("Authorization") String token            ) {
        try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido o ausente");
            }

            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);

            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);

            // Llamar al servicio para guardar el pedido
           

            // Devolver una respuesta con éxito
            return ResponseEntity.ok(pedidoService.guardarPedido( correo));

        } catch (ResponseStatusException ex) {
            // Manejo específico si no se encuentra al usuario
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado: " + ex.getMessage());
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 con detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el pedido: " + e.getMessage());
        }
    }

}
