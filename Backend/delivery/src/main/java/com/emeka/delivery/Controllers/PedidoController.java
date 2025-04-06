package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.PedidoDTO;
import com.emeka.delivery.DTO.UsuarioDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.PedidoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/delivery/v1/pedidos")
@CrossOrigin(origins = "http://localhost:8000")
public class PedidoController {
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/guardarPedido")
    public ResponseEntity<String> guardarPedido(@RequestHeader("Authorization") String token) {
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

    @GetMapping("/buscarRepartidor")
    public ResponseEntity<UsuarioDTO> buscarRepartidor(@RequestHeader("Authorization") String token) {
    
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
    
            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);
    
            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);
    
            // Devolver una respuesta con éxito
            UsuarioDTO usuarioDTO = pedidoService.buscarRepartidor(correo);
    
            // Verificar si el DTO se mapea correctamente antes de retornar
            if (usuarioDTO == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    
            // Imprimir la respuesta antes de retornarla
            System.out.println("Respuesta: " + usuarioDTO.getNombre());
    
            return ResponseEntity.ok(usuarioDTO);
    
  
    }

    @PostMapping("/actualizarEstadoDelPedido")
    public ResponseEntity<String> actualizarEstadoDelPedido(@RequestHeader("Authorization") String token) {
        try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
    
            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);
    
            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);
    
           
            return ResponseEntity.ok(pedidoService.actualizarEstadoDelPedido(correo));
    
        } catch (ResponseStatusException ex) {
            System.out.println("Error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/listaDePedidos")
    public List<PedidoDTO> obtenerPedidos() {
        return pedidoService.obtenerPedidos();
    }
    

    
}
