package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.EstadoDTO;
import com.emeka.delivery.Services.EstadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/delivery/v1/estados")
@CrossOrigin(origins = "http://localhost:8000")
@Tag(name = "Estados", description = "Gestión de los estados de las entidades en el sistema.")
public class EstadoController {
    @Autowired
    private EstadoService estadoService;

    @Operation(summary = "Obtiene todos los estados", description = "Este endpoint permite obtener todos los estados disponibles en el sistema.")
    @GetMapping("/obtenerEstados")
    public List<EstadoDTO> obtenerEstados() {
        return estadoService.obtenerEstados();
    }
    
}
