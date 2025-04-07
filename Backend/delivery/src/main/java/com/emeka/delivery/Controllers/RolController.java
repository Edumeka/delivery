package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.RolDTO;
import com.emeka.delivery.Services.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/delivery/v1/roles")
@Tag(name = "Roles", description = "Controlador para gestionar los roles dentro de la aplicación.")
public class RolController {
    @Autowired
    private RolService rolService;

    @Operation(summary = "Obtener todos los roles", description = "Devuelve una lista de todos los roles disponibles en la aplicación.")
    @GetMapping("/obtenerRoles")
    public List<RolDTO> obtenerRoles() {
        return rolService.obtenerRoles();
    }
    
}
