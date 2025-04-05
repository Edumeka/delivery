package com.emeka.delivery.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.CarritoDTO;
import com.emeka.delivery.Repositories.CarritoRepository;
import com.emeka.delivery.Repositories.EmpresaRepository;
import com.emeka.delivery.Repositories.EstadoRepository;
import com.emeka.delivery.Repositories.PedidoProductoRepository;
import com.emeka.delivery.Repositories.PedidoRepository;
import com.emeka.delivery.Repositories.ProductoRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.Carrito;
import com.emeka.delivery.models.Empresa;
import com.emeka.delivery.models.Estado;
import com.emeka.delivery.models.Pedido;
import com.emeka.delivery.models.PedidoProducto;
import com.emeka.delivery.models.PedidoProductoId;
import com.emeka.delivery.models.Producto;
import com.emeka.delivery.models.Usuario;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {
     @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;  
    
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private PedidoProductoRepository detallePedidoRepository;

    @Autowired
    private ModelMapper modelMapper;
   
    @Transactional
    public String guardarPedido(String correo) {
        // 1. Buscar al usuario comprador
        Usuario comprador = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    
        // 2. Obtener los items del carrito del usuario
        List<Carrito> carritos = carritoRepository.findByUsuario(comprador);
    
        if (carritos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito está vacío");
        }
    
        // 3. Validar que todos los productos sean de la misma empresa
        Empresa empresaDelPedido = carritos.get(0).getProducto().getEmpresa();
    
        boolean mismaEmpresa = carritos.stream()
                .allMatch(c -> c.getProducto().getEmpresa().getIdEmpresa() == empresaDelPedido.getIdEmpresa());
    
        if (!mismaEmpresa) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todos los productos deben ser de la misma empresa");
        }
    
        // 4. Crear un nuevo pedido
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setComprador(comprador);
        pedido.setEmpresa(empresaDelPedido);
        pedido.setPago(null);
        pedido.setMontoTotalDeProductos(
                carritos.stream().mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario()).sum());
    // Buscar el estado "En Proceso"
Estado estadoEnProceso = estadoRepository.findByEstado("En Proceso")
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado 'En Proceso' no encontrado"));

pedido.setEstado(estadoEnProceso); // Asignar el estado al pedido

        // 5. Guardar el pedido en base de datos
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

    
        // 6. Mapear cada carrito a un detalle de pedido
        for (Carrito item : carritos) {
            PedidoProducto detalle = new PedidoProducto();
              // Crear la clave compuesta
        PedidoProductoId id = new PedidoProductoId(pedidoGuardado.getIdPedido(), item.getProducto().getIdProducto());
        detalle.setId(id); // Asignar el ID compuesto
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setSubTotal(item.getCantidad() * item.getPrecioUnitario());
            detallePedidoRepository.save(detalle);
        }
    
        // 7. Limpiar el carrito del usuario
        carritoRepository.deleteAll(carritos);
    
        return "Pedido guardado con éxito";
    }
    

}
