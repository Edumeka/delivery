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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trabajosrealizados")
public class TrabajoRealizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtrabajo")
    private int idTrabajo;
    @Column(name = "kmrecorrido")
    private double kmRecorrido;

    private double ganancia;  
    
    private LocalDateTime fecha;
    @OneToOne
    @JoinColumn(name = "idpedido", referencedColumnName = "idpedido")
    private Pedido pedido;
@OneToOne
@JoinColumn(name = "idrepartidor", referencedColumnName = "idusuario")
    private Usuario repartidor;
}
