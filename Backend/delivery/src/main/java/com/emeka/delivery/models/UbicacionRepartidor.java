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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ubicacionesrepartidores")
public class UbicacionRepartidor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idubicacionrepartidor")
    private int idUbicacionRepartidor;

  @Column(name = "ubicacion", columnDefinition = "geometry(Point, 4326)")
    private org.locationtech.jts.geom.Point ubicacion;

    @OneToOne
    @JoinColumn(name="idrepartidor", referencedColumnName = "idusuario")
    private Usuario repartidor;

}
