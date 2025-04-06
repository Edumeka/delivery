package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.EstadoDTO;
import com.emeka.delivery.Services.EstadoService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/delivery/v1/estados")
@CrossOrigin(origins = "http://localhost:8000")
public class EstadoController {
    @Autowired
    private EstadoService estadoService;

    @GetMapping("/obtenerEstados")
    public List<EstadoDTO> obtenerEstados() {
        return estadoService.obtenerEstados();
    }
    
}
