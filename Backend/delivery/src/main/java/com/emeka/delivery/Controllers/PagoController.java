package com.emeka.delivery.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.PagoRespuestaDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/delivery/v1/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con el manejo de pagos en el sistema.")
public class PagoController {
        @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private PagoService pagoService;
    
    @Operation(summary = "Realiza un pago", description = "Este endpoint permite realizar un pago utilizando la información proporcionada en el cuerpo de la solicitud.")
    @PostMapping("/pagar")
    public ResponseEntity<String> guardarPago(@RequestHeader("Authorization") String token,@RequestBody PagoRespuestaDTO pagoDTO) {
        try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido o ausente");
            }

            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);

            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);


            // Devolver una respuesta con éxito
            return ResponseEntity.ok(pagoService.guardarPago(pagoDTO, correo));

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
