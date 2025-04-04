package com.emeka.delivery.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "estados")
public class Estado {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "idestado")
    private int idEstado;
    
    private String estado;
}
