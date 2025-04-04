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
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idempresa")
    private int idEmpresa;

    private String empresa;

    
    private String rtn;

    @Column(name = "costoenvio")
    private double costoEnvio;

    @OneToOne
    @JoinColumn(name = "idusuarioadministrador", referencedColumnName = "idusuario")
    private Usuario administradorEmpresa;
    
}
