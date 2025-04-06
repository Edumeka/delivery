package com.emeka.delivery.models;

import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenia;
    private String dni;
    private String telefono;

    @OneToOne
    @JoinColumn(name = "idestado", referencedColumnName = "idestado")
    private Estado estado;

    @OneToOne
    @JoinColumn(name = "idrol", referencedColumnName = "idrol")
    private Rol rol;

@OneToMany(mappedBy = "comprador")
private Set<Pedido> pedidos;  // Relación OneToMany con Pedido
}
