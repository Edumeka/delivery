package com.emeka.delivery.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeka.delivery.DTO.ProductoMasVendidoDTO;
import com.emeka.delivery.Repositories.ProductoMasVendidoRepository;
import com.emeka.delivery.models.ProductoMasVendido;

@Service
public class ProductoMasVendidoService {
  @Autowired
private ProductoMasVendidoRepository productoMasVendidoRepository;

@Autowired
private ModelMapper modelMapper;

/**
 * Se obtiene una lista de productos mas vendidos
 * y se coloca el producto mas vendido al principio de la lista
 */
public List<ProductoMasVendidoDTO> productoMasVendidos() {
  List<ProductoMasVendido> productoMasVendidos = productoMasVendidoRepository.findAll();

  List<ProductoMasVendidoDTO> productoMasVendidoDTO = productoMasVendidos.stream()
    .sorted((p1, p2) -> Integer.compare(p2.getCantidadVendida(), p1.getCantidadVendida()))
    .map(producto -> modelMapper.map(producto, ProductoMasVendidoDTO.class))
    .toList();
  return productoMasVendidoDTO;
}

}
