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
public class PagoDTO {
     private int idPago;
   
    private String factura;
    private double totalFactura;
    private LocalDateTime fecha;
    private MetodoPagoDTO metodoPago;
}
