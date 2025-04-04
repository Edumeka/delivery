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

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "lugares")
public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlugar")
    private int idLugar;

    private String lugar;
    private String tipo;
    
    @OneToOne(optional = true)
    @JoinColumn(name = "idlugarsuperior", referencedColumnName = "idlugar")  // Relaciona con la misma tabla
    private Lugar lugarSuperior;
}
