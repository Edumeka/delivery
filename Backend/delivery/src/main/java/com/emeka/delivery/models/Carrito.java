package com.emeka.delivery.models;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "carritos")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcarrito")
    private int idCarrito;

    @OneToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    private Producto producto;

    private int cantidad;

    @Column(name = "preciounitario")
    private double precioUnitario;

    @Column(name = "subtotal")
    private double subTotal;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;      
        
}
