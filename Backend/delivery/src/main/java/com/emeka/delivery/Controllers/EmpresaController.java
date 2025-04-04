package com.emeka.delivery.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.DireccionDTO;
import com.emeka.delivery.DTO.EmpresaDTO;
import com.emeka.delivery.Services.DireccionService;
import com.emeka.delivery.Services.EmpresaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;


@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://127.0.0.1:8000/"})
@RestController
@RequestMapping("/delivery/v1/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

     @Autowired
    private DireccionService direccionService;   
    
    @PostMapping("/guardarEmpresa")  
    public String guardarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        return empresaService.guardarEmpresa(empresaDTO);
    }

    @GetMapping("/obtenerEmpresas/{idDireccion}")
    public List<EmpresaDTO> obtenerEmpresasCercanas(@PathVariable int idDireccion) {
        return empresaService.obtenerEmpresasCercanas(idDireccion);
    }

    @PostMapping("/crearDireccionEmpresa")
    public String crearDireccionAEmpresa(@RequestBody DireccionDTO direccionDTO) {
        return direccionService.crearDireccionAEmpresa(direccionDTO);        
        
    }
    
    
}
