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
public class ProductoMasVendidoDTO {
    private int idProductoMasVendido;

    private int cantidadVendida;

    private LocalDateTime ultimaFechaVenta;

    private double totalIngresos;

    private ProductoDTO producto;
}
