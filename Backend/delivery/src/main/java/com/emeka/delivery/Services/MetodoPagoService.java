package com.emeka.delivery.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeka.delivery.DTO.MetodoPagoDTO;
import com.emeka.delivery.Repositories.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private ModelMapper modelMapper;

   public List<MetodoPagoDTO> obtenerMetodosPagos() {
        return metodoPagoRepository.findAll().stream()
                .map(metodoPago -> modelMapper.map(metodoPago, MetodoPagoDTO.class))
                .toList();
   }

}
