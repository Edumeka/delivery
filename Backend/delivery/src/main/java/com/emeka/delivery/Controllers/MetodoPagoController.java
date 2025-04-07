package com.emeka.delivery.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.MetodoPagoDTO;
import com.emeka.delivery.Services.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/delivery/v1/metodopago")
@Tag(name = "Métodos de Pago", description = "Controlador para gestionar los métodos de pago de los pedidos.")
public class MetodoPagoController {
    @Autowired
    private MetodoPagoService metodoPagoService;

    @Operation(summary = "Obtiene todos los métodos de pago", description = "Este endpoint devuelve una lista de todos los métodos de pago disponibles en el sistema.")
    @GetMapping("/obtenerMetodoPago")
    public List<MetodoPagoDTO> obtenerMetodosPagos() {
        return metodoPagoService.obtenerMetodosPagos();
    }
    
    
}
