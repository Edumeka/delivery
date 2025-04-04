package com.emeka.delivery.models;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int idDireccion;
    
    private String direccion;
    private String descripcion;
    @OneToOne
    @JoinColumn(name = "idlugar", referencedColumnName = "idlugar")
    private Lugar lugar;
    
    @Column(name = "ubicacion", columnDefinition = "geometry(Point, 4326)")
    private org.locationtech.jts.geom.Point ubicacion;

    @OneToOne(optional = true)
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    private Usuario usuario;

    @OneToOne(optional = true)
    @JoinColumn(name = "idempresa", referencedColumnName = "idempresa")
    private Empresa empresa;
}
