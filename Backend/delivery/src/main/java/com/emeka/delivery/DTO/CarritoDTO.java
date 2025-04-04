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
public class CarritoDTO {
     private int idCarrito;

    private UsuarioDTO usuario;

    private ProductoDTO producto;

    private int cantidad;

    private double precioUnitario;

    private double subTotal;

    private LocalDateTime fechaCreacion;   
}
