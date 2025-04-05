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

     /**
     * Método para obtener empresas dentro de un radio específico desde una dirección dada.
     * Este método utiliza la ubicación de la dirección del usuario para calcular la distancia a cada empresa.
     * Si la distancia es menor o igual al radio especificado, la empresa se incluye en la lista de resultados.
     *
     * @param idDireccion ID de la dirección del usuario desde la cual se calculará la distancia.
     * @return Lista de objetos EmpresaDTO que representan las empresas dentro del radio especificado.
     * Si no se encuentra la dirección, se retorna una lista vacía.
     */
    public List<EmpresaDTO> obtenerEmpresasCercanas(int idDireccion) {
        double radio = 10; // Radio de 10 km

        // Obtener la dirección del usuario
        Optional<Direccion> direccionOpt = direccionRepository.findById(idDireccion);
        if (direccionOpt.isEmpty()) {
            return Collections.emptyList();  // Si no se encuentra la dirección, devolver lista vacía
        }

        Direccion direccionUsuario = direccionOpt.get();
        Point ubicacionUsuario = direccionUsuario.getUbicacion();  // Obtener ubicación de la dirección del usuario

        // Depuración: Verifica las coordenadas del usuario
        System.out.println("Ubicación del usuario: " + ubicacionUsuario);

        // Obtener todas las empresas
        List<Empresa> empresas = empresaRepository.findAll();

        // Filtrar empresas dentro del rango usando Streams
        List<Empresa> empresasCercanas = empresas.stream()
                .filter(empresa -> {
                    Optional<Direccion> direccionEmpresaOpt = direccionRepository.findByEmpresa(empresa); // Obtener dirección de la empresa

                    // Verificar si la empresa tiene dirección y calcular la distancia
                    if (direccionEmpresaOpt.isPresent()) {
                        Direccion direccionEmpresa = direccionEmpresaOpt.get();
                        Point ubicacionEmpresa = direccionEmpresa.getUbicacion();

                        // Depuración: Verifica las coordenadas de la empresa
                        System.out.println("Ubicación de la empresa " + empresa.getEmpresa() + ": " + ubicacionEmpresa);

                        // Calcular la distancia entre la ubicación del usuario y la empresa
                        double distancia = calcularDistancia(ubicacionUsuario, ubicacionEmpresa);

                        // Depuración: Verifica la distancia calculada
                        System.out.println("Distancia entre el usuario y " + empresa.getEmpresa() + ": " + distancia + " km");

                        // Incluir solo si la distancia es menor o igual a 10 km
                        if (distancia <= radio) {
                            System.out.println("Empresa " + empresa.getEmpresa() + " está dentro de los " + radio + " km.");
                            return true;  // Incluir esta empresa
                        }
                    }
                    return false; // No incluir si no está dentro del rango
                })
                .collect(Collectors.toList());  // Recopilar todas las empresas cercanas

        // Si no hay empresas cercanas, devolver lista vacía
        if (empresasCercanas.isEmpty()) {
            System.out.println("No se encontraron empresas dentro del rango de " + radio + " km.");
        }

        // Mapear la lista de Empresas a EmpresaDTO usando ModelMapper
        return empresasCercanas.stream()
                .map(empresa -> modelMapper.map(empresa, EmpresaDTO.class))
                .collect(Collectors.toList());
    }


    /**
     * Método para calcular la distancia entre dos puntos geográficos.
     *
     * @param ubicacionUsuario Ubicación del usuario.
     * @param ubicacionEmpresa Ubicación de la empresa.
     * @return Distancia en kilómetros entre los dos puntos.
     */
    private double calcularDistancia(Point ubicacionUsuario, Point ubicacionEmpresa) {
        // Implementa aquí la lógica para calcular la distancia entre dos puntos geográficos.
        // Puedes usar la fórmula de Haversine o alguna otra fórmula adecuada.

        // Ejemplo de implementación (reemplaza esto con tu lógica real):
        double lat1 = ubicacionUsuario.getY();
        double lon1 = ubicacionUsuario.getX();
        double lat2 = ubicacionEmpresa.getY();
        double lon2 = ubicacionEmpresa.getX();

        // Convertir grados a radianes
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // Diferencia de latitud y longitud
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        // Fórmula de Haversine
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Radio de la Tierra en kilómetros (aproximadamente)
        double radioTierra = 6371;

        // Calcular la distancia
        return radioTierra * c;
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
    

    public double calcularDistanciaDelUsuario(int idEmpresa, String correo) {

        // Obtener la dirección del usuario por correo
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
    
            // Obtener la dirección del usuario
            Optional<Direccion> direccionUsuarioOpt = direccionRepository.findFirstByUsuario(usuario);
            if (direccionUsuarioOpt.isPresent()) {
                Direccion direccionUsuario = direccionUsuarioOpt.get();
                Point ubicacionUsuario = direccionUsuario.getUbicacion(); // Obtener ubicación de la dirección del usuario
    
                // Obtener la empresa por ID
                Optional<Empresa> empresaOpt = empresaRepository.findById(idEmpresa);
                if (empresaOpt.isPresent()) {
                    Empresa empresa = empresaOpt.get();
    
                    // Obtener la dirección de la empresa
                    Optional<Direccion> direccionEmpresaOpt = direccionRepository.findByEmpresa(empresa);
                    if (direccionEmpresaOpt.isPresent()) {
                        Direccion direccionEmpresa = direccionEmpresaOpt.get();
                        Point ubicacionEmpresa = direccionEmpresa.getUbicacion(); // Obtener ubicación de la dirección de la empresa
    
                        System.out.println("Ubicación del usuario: " + ubicacionUsuario);
                        System.out.println("Ubicación de la empresa: " + ubicacionEmpresa);
                        // Calcular y devolver la distancia entre el usuario y la empresa
                        return calcularDistancia(ubicacionUsuario, ubicacionEmpresa);
                    }
                }
            }
        }
    
        // Retorna -1 en caso de que no se encuentren los datos necesarios
        return -1;
    }
}
