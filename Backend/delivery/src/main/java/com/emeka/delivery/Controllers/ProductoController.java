package com.emeka.delivery.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.EmpresaDTO;
import com.emeka.delivery.DTO.ProductoDTO;
import com.emeka.delivery.DTO.ProductoMasVendidoDTO;
import com.emeka.delivery.Security.JwtGenerator;
import com.emeka.delivery.Services.ProductoMasVendidoService;
import com.emeka.delivery.Services.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;



@CrossOrigin(origins = {"http://localhost:8000", "https://localhost:8000", "http://127.0.0.1:8000/"})
@RestController
@RequestMapping("/delivery/v1/productos")
@Tag(name = "Productos", description = "Controlador para gestionar productos en el sistema.")
public class ProductoController {
     @Autowired
     private ProductoService productoService;
     @Autowired
     private ProductoMasVendidoService productoMasVendidoService;

      @Autowired
    private JwtGenerator jwtGenerator;
    
    @Operation(summary = "Obtener productos de una empresa", description = "Obtiene una lista de productos asociados a una empresa específica usando su ID.")
     @GetMapping("/{idEmpresa}")     
      public ResponseEntity<List<ProductoDTO>> productosDeLaEmpresa(@RequestHeader("Authorization") String token, @PathVariable int idEmpresa) {
    
        try {
            // Verificar que el token esté presente y comience con "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
            }
    
            // Eliminar el prefijo "Bearer " para obtener el token real
            token = token.substring(7);
    
            // Intentar extraer el correo desde el token
            String correo = jwtGenerator.getUsernameFromToken(token);           
          
           
      // Llamar al servicio para crear la dirección
            // Devolver una respuesta con éxito
            return ResponseEntity.ok(productoService.productosDeLaEmpresa(idEmpresa, correo));
    
        } catch (ResponseStatusException ex) {
            // Manejo específico si no se encuentra al usuario
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // En caso de error interno, devolver un estado 500 con detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }



    }

    @Operation(summary = "Obtener empresa asociada a un producto", description = "Obtiene la información de la empresa asociada a un producto específico usando su ID.")
        @GetMapping("/obtenerEmpresaPorProducto/{idProducto}")
    public EmpresaDTO obtenerEmpresaPorIdProducto(@PathVariable int idProducto) {
        return productoService.obtenerEmpresaPorIdProducto(idProducto);
    }
    
    @Operation(summary = "Obtener los productos más vendidos", description = "Devuelve una lista de los productos más vendidos en la tienda.")
    @GetMapping("/productosMasVendidos")
    public List<ProductoMasVendidoDTO> productoMasVendidos() {
        return productoMasVendidoService.productoMasVendidos();
    }
    
}
