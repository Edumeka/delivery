package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.RolDTO;
import com.emeka.delivery.Services.RolService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/delivery/v1/roles")
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping("/obtenerRoles")
    public List<RolDTO> obtenerRoles() {
        return rolService.obtenerRoles();
    }
    
}
