package com.emeka.delivery.models;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpedido")
    private int idPedido;
    @Column(name = "fechapedido")
    private LocalDateTime fechaPedido;
    @Column(name = "fechafinal")
    private LocalDateTime fechaFinal;
    @Column(name = "costoenviototal")
    private double costoEnvioTotal;
    @Column(name = "montototaldeproductos")
    private double montoTotalDeProductos;

    @ManyToOne
    @JoinColumn(name = "idcomprador", referencedColumnName = "idusuario")
    private Usuario comprador;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idrepartidor", referencedColumnName = "idusuario")
    private Usuario repartidor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idestado", referencedColumnName = "idestado")
    private Estado estado;

    @OneToOne
    @JoinColumn(name = "idpago", referencedColumnName = "idpago")   
    private Pago pago;

    @OneToOne
    @JoinColumn(name = "idempresa", referencedColumnName = "idempresa")
    private Empresa empresa;    

       @ManyToMany
    @JoinTable(
        name = "pedidosproductos", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "idpedido"), // Clave foránea a la tabla de pedidos
        inverseJoinColumns = @JoinColumn(name = "idproducto") // Clave foránea a la tabla de productos
    )
    private Set<Producto> productos; // Usamos un Set para evitar duplicados
}
