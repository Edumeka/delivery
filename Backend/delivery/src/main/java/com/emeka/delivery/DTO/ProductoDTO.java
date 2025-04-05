package com.emeka.delivery.DTO;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
     private int idProducto;

    private String producto;

    private String descripcion;

    private double precio;
    
    private String imagen;

    private CategoriaDTO categoria;


    private EmpresaDTO empresa;

@JsonIgnore
    private Set<PedidoDTO> pedidos;
}
