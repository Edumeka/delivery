package com.emeka.delivery.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Point;

import com.emeka.delivery.DTO.EmpresaDTO;
import com.emeka.delivery.Repositories.DireccionRepository;
import com.emeka.delivery.Repositories.EmpresaRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.Direccion;
import com.emeka.delivery.models.Empresa;
import com.emeka.delivery.models.Usuario;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Método para obtener empresas dentro de un radio específico desde una dirección dada.
     * Este método utiliza la ubicación de la dirección del usuario para calcular la distancia a cada empresa.
     * Si la distancia es menor o igual al radio especificado, la empresa se incluye en la lista de resultados.
     * 
     * @param idDireccion ID de la dirección del usuario desde la cual se calculará la distancia.
     * @return Lista de objetos EmpresaDTO que representan las empresas dentro del radio especificado.
     *         Si no se encuentra la dirección, se retorna una lista vacía.
     */
    public List<EmpresaDTO> obtenerEmpresasCercanas(int idDireccion) {
        double radio=10;
        // Obtener la dirección del usuario
        Optional<Direccion> direccionOpt = direccionRepository.findById(idDireccion);
        if (direccionOpt.isEmpty()) {
            return Collections.emptyList();
        }
    
        Direccion direccionUsuario = direccionOpt.get();
        Point ubicacionUsuario = direccionUsuario.getUbicacion(); // Ubicación de la dirección elegida
    
        // Obtener todas las empresas
        List<Empresa> empresas = empresaRepository.findAll();
    
        // Filtrar empresas dentro del rango usando Streams
        List<Empresa> empresasCercanas = empresas.stream()
        .filter(empresa -> {
            Optional<Direccion> direccionEmpresaOpt = direccionRepository.findByEmpresa(empresa); // Obtener dirección de la empresa
         
         
            return direccionEmpresaOpt.isPresent() && 
                   calcularDistancia(ubicacionUsuario, direccionEmpresaOpt.get().getUbicacion()) <= radio;
        })
        
            .toList();
    
        // Mapear la lista de Empresa a EmpresaDTO usando ModelMapper
        return empresasCercanas.stream()
            .map(empresa -> modelMapper.map(empresa, EmpresaDTO.class))
            .collect(Collectors.toList());
    }
    
    private double calcularDistancia(Point p1, Point p2) {
        double lat1 = p1.getX();
        double lon1 = p1.getY();
        double lat2 = p2.getX();
        double lon2 = p2.getY();
    
        // Fórmula de Haversine para calcular distancia entre dos puntos geográficos
        double radioTierra = 6371; // Radio de la Tierra en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    
        return radioTierra * c; // Retorna la distancia en kilómetros
    }

    /*
     * Método para guardar una nueva empresa en la base de datos.
     * Verifica si ya existe una empresa con el mismo nombre antes de guardar.
     */
    public String guardarEmpresa(EmpresaDTO empresaDTO) {
        // Verificar si ya existe una empresa con el mismo nombre
        Optional<Empresa> empresaExistente = empresaRepository.findByEmpresa(empresaDTO.getEmpresa());
        if (empresaExistente.isPresent()) {
            return "La empresa ya existe en la base de datos.";
        }
    
        // Verificar si el usuario administrador existe en la base de datos
        Optional<Usuario> adminOpt = usuarioRepository.findById(empresaDTO.getAdministradorEmpresa().getIdUsuario());
        if (adminOpt.isEmpty()) {
            return "El usuario administrador no existe.";
        }
    
        // Verificar que el costo de envío no sea negativo o inválido
        if (empresaDTO.getCostoEnvio() <= 0) {
            return "El costo de envío debe ser un valor positivo.";
        }
    
        // Convertir el DTO a entidad y asignar el administrador
        Empresa empresa = modelMapper.map(empresaDTO, Empresa.class);
        empresa.setAdministradorEmpresa(adminOpt.get());
    
        // Guardar la empresa en la base de datos
        empresaRepository.save(empresa);
        return "Empresa guardada correctamente";
    }
    

    
}
