package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeka.delivery.DTO.LugarDTO;
import com.emeka.delivery.Services.DireccionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;



@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://127.0.0.1:8000/"})
@RestController
@RequestMapping("/delivery/v1/direcciones")
public class DireccionController {
    
    @Autowired
    private DireccionService direccionService;


    @GetMapping("/paises")
    public ResponseEntity<List<LugarDTO>> obtenerPaises(@RequestHeader("Authorization") String token) {
        try {
            // Verificar que el token esté presente y comienza con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
            }
            
            // Si el token es válido, llamar al servicio para obtener los países
            List<LugarDTO> paises = direccionService.obtenerPaises();
            
            // Si hay países, devolverlos con el estado 200 OK
            return ResponseEntity.ok(paises);
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/departamentos/{idLugarSuperior}")
    public ResponseEntity<List<LugarDTO>> obtenerDepartamentos(@RequestHeader("Authorization") String token,@PathVariable  int idLugarSuperior) {
        
        try {
            // Verificar que el token esté presente y comienza con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
            }
            
            // Si el token es válido, llamar al servicio para obtener los países
            List<LugarDTO> departamentos = direccionService.obtenerDepartamentos(idLugarSuperior);
            
            // Si hay países, devolverlos con el estado 200 OK
            return ResponseEntity.ok(departamentos);
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/municipios/{idLugarSuperior}")
    public ResponseEntity<List<LugarDTO>> obtenerMunicipios(@RequestHeader("Authorization") String token,@PathVariable int idLugarSuperior) {
        
        try {
            // Verificar que el token esté presente y comienza con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
            }
            
            // Si el token es válido, llamar al servicio para obtener los países
            List<LugarDTO> municipios = direccionService.obtenerMunicipios(idLugarSuperior);
            
            // Si hay países, devolverlos con el estado 200 OK
            return ResponseEntity.ok(municipios);
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
