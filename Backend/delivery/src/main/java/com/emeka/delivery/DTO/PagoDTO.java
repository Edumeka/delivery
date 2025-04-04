package com.emeka.delivery.DTO;

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
    private MetodoPagoDTO metodoPago;
}
