package com.emeka.delivery.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.DireccionDTO;
import com.emeka.delivery.DTO.LugarDTO;
import com.emeka.delivery.Repositories.DireccionRepository;
import com.emeka.delivery.Repositories.EmpresaRepository;
import com.emeka.delivery.Repositories.LugarRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.Direccion;
import com.emeka.delivery.models.Empresa;
import com.emeka.delivery.models.Lugar;
import com.emeka.delivery.models.Usuario;

@Service
public class DireccionService {
    @Autowired
    private DireccionRepository direccionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ModelMapper modelMapper;


  /**
 * Metodo que crea una direccion de un cliente
 * @param direccionDTO
 * @return String
 */
public String crearDireccionCliente(DireccionDTO direccionDTO, String correo) {

    if (direccionDTO == null) {
        return "El DTO de dirección o el usuario no pueden ser nulos";
    }    
     // Buscar el usuario en la base de datos utilizando el correo extraído del token
    Usuario usuarioExistente = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    
   
    try {
    

        // Verificar si ya existe una dirección con ese usuario y dirección
        Direccion direccionExistente = direccionRepository.findByUsuarioAndDireccion(usuarioExistente, direccionDTO.getDireccion());

        if (direccionExistente != null) {
            return "La dirección ya está registrada para este usuario";
        }

        // Convertir el DTO a la entidad
        Direccion direccion = modelMapper.map(direccionDTO, Direccion.class);
        direccion.setUsuario(usuarioExistente); // Asignar el usuario encontrado

        // Guardar la entidad en la base de datos
        direccionRepository.save(direccion);

        return "Se ha creado la dirección con éxito";
    } catch (Exception e) {
        return "Error al crear la dirección: " + e.getMessage();
    }
}




/**
 * Metodo que obtiene todas las direcciones de un cliente
 * @param idUsuario
 * @return List<DireccionDTO>
 */
public List<DireccionDTO> obtenerDireccionesDelCliente(String correo) {
    Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

    if (usuario.isPresent()) {
        // Buscar TODAS las direcciones del usuario
        List<Direccion> direcciones = direccionRepository.findByUsuario(usuario.get());

        // Convertir cada dirección a DireccionDTO
        return direcciones.stream()
                .map(direccion -> modelMapper.map(direccion, DireccionDTO.class))
                .collect(Collectors.toList());
    } else {
        return Collections.emptyList(); // Retorna lista vacía en vez de null
    }
}



public String crearDireccionAEmpresa(DireccionDTO direccionDTO) {
    if (direccionDTO == null || direccionDTO.getEmpresa() == null) {
        return "El DTO de dirección o la empresa no pueden ser nulos";
    }

    try {
        // Buscar el usuario en la base de datos
        Empresa empresa = empresaRepository.findById(direccionDTO.getEmpresa().getIdEmpresa())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si ya existe una dirección con ese usuario y dirección
        Direccion direccionExistente = direccionRepository.findByEmpresaAndDireccion(empresa, direccionDTO.getDireccion());

        if (direccionExistente != null) {
            return "La dirección ya está registrada para esta empresa";
        }

        // Convertir el DTO a la entidad
        Direccion direccion = modelMapper.map(direccionDTO, Direccion.class);
        direccion.setEmpresa(empresa); // Asignar el usuario encontrado

        // Guardar la entidad en la base de datos
        direccionRepository.save(direccion);

        return "Se ha creado la dirección con éxito";
    } catch (Exception e) {
        return "Error al crear la dirección: " + e.getMessage();
    }
}

public List<LugarDTO> obtenerPaises() {
    // Obtener solo los lugares que sean de tipo "PAIS"
    List<Lugar> paises = lugarRepository.findByTipo("PAIS");

    // Convertir cada entidad Lugar en un LugarDTO
    return paises.stream()
                 .map(pais -> modelMapper.map(pais, LugarDTO.class))
                 .collect(Collectors.toList());
}

public List<LugarDTO> obtenerDepartamentos(int idLugarSuperior) {
    // Intentamos obtener el lugar superior por su id
    Optional<Lugar> lugarSeleccionado = lugarRepository.findById(idLugarSuperior);

    if (lugarSeleccionado.isPresent()) {
        // Obtener los departamentos que tienen como lugar superior el lugar recibido y que sean de tipo "DEPARTAMENTO"
        List<Lugar> departamentos = lugarRepository.findByLugarSuperiorAndTipo(lugarSeleccionado.get(), "DEPARTAMENTO");

        // Convertir cada entidad Lugar en un LugarDTO
        return departamentos.stream()
                 .map(departamento -> modelMapper.map(departamento, LugarDTO.class))
                 .collect(Collectors.toList());
    } else {
        // Si no se encuentra el lugar, se retorna una lista vacía
        return new ArrayList<>();
    }
}


public List<LugarDTO> obtenerMunicipios(int idLugarSuperior) {
    // Intentamos obtener el lugar superior por su id
    Optional<Lugar> lugarSeleccionado = lugarRepository.findById(idLugarSuperior);

    if (lugarSeleccionado.isPresent()) {
        // Obtener los municipios que tienen como lugar superior el lugar recibido y que sean de tipo "MUNICIPIO"
        List<Lugar> municipios = lugarRepository.findByLugarSuperiorAndTipo(lugarSeleccionado.get(), "MUNICIPIO");

        // Convertir cada entidad Lugar en un LugarDTO
        return municipios.stream()
                 .map(municipio -> modelMapper.map(municipio, LugarDTO.class))
                 .collect(Collectors.toList());
    } else {
        // Si no se encuentra el lugar, se retorna una lista vacía
        return new ArrayList<>();
    }
}



}
