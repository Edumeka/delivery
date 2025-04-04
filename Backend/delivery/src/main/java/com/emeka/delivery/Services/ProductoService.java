package com.emeka.delivery.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeka.delivery.DTO.ProductoDTO;
import com.emeka.delivery.Repositories.EmpresaRepository;
import com.emeka.delivery.Repositories.ProductoRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.Empresa;
import com.emeka.delivery.models.Producto;
import com.emeka.delivery.models.Usuario;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;    

    @Autowired
    private ModelMapper modelMapper;


    public List<ProductoDTO> productosDeLaEmpresa(int idEmpresa, String correo) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
    
        // Obtener la empresa por ID
        Empresa empresaElegida = empresaRepository.findById(idEmpresa).orElse(null);
    
        if (usuario.isPresent() && empresaElegida != null) {
            // Obtener productos de esa empresa
            List<Producto> productos = productoRepository.findByEmpresa(empresaElegida);
    
            // Convertir los productos a DTOs
            return productos.stream()
                    .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList(); // Retorna lista vacía si el usuario no existe o la empresa no se encontró
        }
    }
    
}
