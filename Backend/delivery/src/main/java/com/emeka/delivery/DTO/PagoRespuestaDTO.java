package com.emeka.delivery.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagoRespuestaDTO {
      private int idPago;
   
    private String factura;
    private double totalFactura;
    private LocalDateTime fecha;
    private double costoEnvio;
    private double kmRecorridos;
    private double gananciaRepartidor;
    private MetodoPagoDTO metodoPago;


}
