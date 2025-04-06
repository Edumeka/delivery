package com.emeka.delivery.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeka.delivery.Repositories.ProductoMasVendidoRepository;
import com.emeka.delivery.models.Producto;
import com.emeka.delivery.models.ProductoMasVendido;

@Service
public class ProductoMasVendidoService {
  @Autowired
private ProductoMasVendidoRepository productoMasVendidoRepository;


}
