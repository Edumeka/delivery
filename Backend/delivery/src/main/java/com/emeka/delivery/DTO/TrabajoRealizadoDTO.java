package com.emeka.delivery.DTO;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrabajoRealizadoDTO {
     private int idTrabajo;
    private double kmRecorrido;

    private double ganancia;  
    
    private LocalDateTime fecha;
    private PedidoDTO pedido;
    private UsuarioDTO repartidor;
}
