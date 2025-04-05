package com.emeka.delivery.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.MetodoPagoDTO;
import com.emeka.delivery.Services.MetodoPagoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/delivery/v1/metodopago")
public class MetodoPagoController {
    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping("/obtenerMetodoPago")
    public List<MetodoPagoDTO> obtenerMetodosPagos() {
        return metodoPagoService.obtenerMetodosPagos();
    }
    
    
}
