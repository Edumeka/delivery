package com.emeka.delivery.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeka.delivery.DTO.EstadoDTO;
import com.emeka.delivery.Repositories.EstadoRepository;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<EstadoDTO> obtenerEstados() {
        return estadoRepository.findAll().stream()
                .map(estado -> modelMapper.map(estado, EstadoDTO.class))
                .toList();
    }
}
