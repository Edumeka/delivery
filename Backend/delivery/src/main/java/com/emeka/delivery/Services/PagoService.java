package com.emeka.delivery.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.PagoRespuestaDTO;
import com.emeka.delivery.Repositories.MetodoPagoRepository;
import com.emeka.delivery.Repositories.PagoRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.MetodoPago;
import com.emeka.delivery.models.Pago;
import com.emeka.delivery.models.Usuario;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    


   public String guardarPago(PagoRespuestaDTO pagoDTO, String correo){
        // 1. Buscar al usuario comprador
        Usuario comprador = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));   

        // 2. Guardar el pago
        Pago pago = new Pago();

        pago.setFactura(pagoDTO.getFactura());
        pago.setTotalFactura(pagoDTO.getTotalFactura());

        MetodoPago metodoPago = metodoPagoRepository.findById(pagoDTO.getMetodoPago().getIdMetodoPago())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Método de pago no encontrado"));

        // Verificar si el método de pago existe en la base de datos
        pago.setMetodoPago(metodoPago);
        pago.setFecha(LocalDateTime.now());

        Pago ultimoPago =  pagoRepository.save(pago);

        pedidoService.asignarPago(ultimoPago, comprador, pagoDTO.getKmRecorridos(), pagoDTO.getCostoEnvio(), pagoDTO.getGananciaRepartidor());
        

        return "Pago guardado con éxito";
        
    }

}
