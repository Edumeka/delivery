package com.emeka.delivery.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeka.delivery.DTO.RolDTO;
import com.emeka.delivery.Repositories.RolRepository;
import com.emeka.delivery.models.Rol;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<RolDTO> obtenerRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                    .map(rol -> modelMapper.map(rol, RolDTO.class))
                    .toList();
    }
}
