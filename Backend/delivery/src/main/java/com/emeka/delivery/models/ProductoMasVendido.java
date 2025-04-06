package com.emeka.delivery.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="productosmasvendidos")
public class ProductoMasVendido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idproductomasvendido")
    private int idProductoMasVendido;

    @Column(name="cantidadvendida")
    private int cantidadVendida;

    @Column(name = "ultimafechaventa")
    private LocalDateTime ultimaFechaVenta;

    @Column(name="totalingresos")
    private double totalIngresos;

    @ManyToOne
    @JoinColumn(name="idproducto", referencedColumnName = "idproducto")
    private Producto producto;


}
