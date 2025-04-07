package com.emeka.delivery.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.DireccionDTO;
import com.emeka.delivery.DTO.EmpresaDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.DireccionService;
import com.emeka.delivery.Services.EmpresaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;




@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://127.0.0.1:8000/"})
@RestController
@RequestMapping("/delivery/v1/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con la gestión de empresas.")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

     @Autowired
    private DireccionService direccionService;   
         @Autowired
    private JwtGenerator jwtGenerator;

    @Operation(summary = "Guardar nueva empresa", description = "Permite guardar una nueva empresa en el sistema.")
    @PostMapping("/guardarEmpresa")  
    public String guardarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        return empresaService.guardarEmpresa(empresaDTO);
    }

    @Operation(summary = "Obtener empresas cercanas", description = "Obtiene las empresas cercanas a una dirección específica, identificada por el id de la dirección.")
    @GetMapping("/obtenerEmpresas/{idDireccion}")
    public List<EmpresaDTO> obtenerEmpresasCercanas(@PathVariable int idDireccion) {
        return empresaService.obtenerEmpresasCercanas(idDireccion);
    }

    @Operation(summary = "Crear una nueva dirección para una empresa", description = "Permite crear una nueva dirección asociada a una empresa mediante los datos proporcionados en el cuerpo de la solicitud.")
    @PostMapping("/crearDireccionEmpresa")
    public String crearDireccionAEmpresa(@RequestBody DireccionDTO direccionDTO) {
        return direccionService.crearDireccionAEmpresa(direccionDTO);        
        
    }
    
    @Operation(summary = "Calcular la distancia desde el usuario hasta una empresa", description = "Calcula la distancia entre el usuario (basado en su ubicación) y la empresa proporcionada por el id.")
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

@Operation(summary = "Obtener todas las empresas", description = "Devuelve una lista de todas las empresas registradas en el sistema.")
@GetMapping("/obtenerEmpresas")
public List<EmpresaDTO> obtenerEmpresas() {
    return empresaService.obtenerEmpresas();
}

@Operation(summary = "Editar una empresa", description = "Permite actualizar la información de una empresa existente.")
@PostMapping("/editarEmpresa")
public String editarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
    
    return empresaService.editarEmpresa(empresaDTO);
}




    
}
