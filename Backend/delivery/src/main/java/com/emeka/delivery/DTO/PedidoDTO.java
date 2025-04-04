package com.emeka.delivery.DTO;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
     private int idPedido;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaFinal;
    private double costoEnvioTotal;
    private double montoTotalDeProductos;

    private UsuarioDTO comprador;

    private UsuarioDTO repartidor;

    private EstadoDTO estado;
    private PagoDTO pago;

    private EmpresaDTO empresa;    

    private Set<ProductoDTO> productos; // Usamos un Set para evitar duplicados
}
