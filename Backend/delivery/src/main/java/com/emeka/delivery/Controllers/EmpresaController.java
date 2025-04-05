package com.emeka.delivery.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.DireccionDTO;
import com.emeka.delivery.DTO.EmpresaDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.DireccionService;
import com.emeka.delivery.Services.EmpresaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://127.0.0.1:8000/"})
@RestController
@RequestMapping("/delivery/v1/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

     @Autowired
    private DireccionService direccionService;   
         @Autowired
    private JwtGenerator jwtGenerator;

    
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
    
@GetMapping("/obtenerDistancia/{idEmpresa}")
public ResponseEntity<Double> calcularDistanciaDelUsuario(@RequestHeader("Authorization") String token, @PathVariable int idEmpresa) {
   try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return null;
            }

            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);

            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);


            // Devolver una respuesta con éxito
            return ResponseEntity.ok(empresaService.calcularDistanciaDelUsuario(idEmpresa, correo));

        } catch (ResponseStatusException ex) {
            // Manejo específico si no se encuentra al usuario
            return null;
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 con detalles del error
            return null;
        }
}

    
}
