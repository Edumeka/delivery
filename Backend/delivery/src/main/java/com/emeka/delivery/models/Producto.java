package com.emeka.delivery.models;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    private int idProducto;

    private String producto;

    private String descripcion;

    private double precio;
    
    private String imagen;

    @OneToOne
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
    private Categoria categoria;

    @OneToOne
    @JoinColumn(name = "idempresa", referencedColumnName = "idempresa")
    private Empresa empresa;

    @ManyToMany(mappedBy = "productos") // Relación inversa de la relación muchos a muchos
    private Set<Pedido> pedidos;
}   
