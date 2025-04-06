package com.emeka.delivery.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.UsuarioDTO;
import com.emeka.delivery.Repositories.CarritoRepository;
import com.emeka.delivery.Repositories.EstadoRepository;
import com.emeka.delivery.Repositories.PedidoProductoRepository;
import com.emeka.delivery.Repositories.PedidoRepository;
import com.emeka.delivery.Repositories.TrabajoRealizadoRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.Carrito;
import com.emeka.delivery.models.Empresa;
import com.emeka.delivery.models.Estado;
import com.emeka.delivery.models.Pago;
import com.emeka.delivery.models.Pedido;
import com.emeka.delivery.models.PedidoProducto;
import com.emeka.delivery.models.PedidoProductoId;
import com.emeka.delivery.models.TrabajoRealizado;
import com.emeka.delivery.models.Usuario;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private UsuarioRepository usuarioRepository;
 
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private PedidoProductoRepository detallePedidoRepository;

    @Autowired
    private TrabajoRealizadoRepository trabajoRepository;

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

// Buscar el estado "En Proceso" o crearlo si no existe
Estado estadoEnProceso = estadoRepository.findByEstado("En Proceso")
    .orElseGet(() -> {
        Estado nuevoEstado = new Estado();
        nuevoEstado.setEstado("En Proceso");
        return estadoRepository.save(nuevoEstado);
    });


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
    

    
    @Transactional
    public String asignarPago(Pago pago, Usuario comprador, double kmRecorridos, double costoEnvio, double gananciaRepartidor) {
        // 1. Buscar al usuario comprador
        
    
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
        pedido.setPago(pago);
        pedido.setCostoEnvioTotal(costoEnvio+ gananciaRepartidor);
        pedido.setMontoTotalDeProductos(
                carritos.stream().mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario()).sum());

// Buscar el estado "En Proceso" o crearlo si no existe
Estado estadoEnProceso = estadoRepository.findByEstado("En Proceso")
    .orElseGet(() -> {
        Estado nuevoEstado = new Estado();
        nuevoEstado.setEstado("En Proceso");
        return estadoRepository.save(nuevoEstado);
    });


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

        TrabajoRealizado trabajoRealizado = new TrabajoRealizado();
        trabajoRealizado.setPedido(pedidoGuardado);
        trabajoRealizado.setFecha(LocalDateTime.now());
        trabajoRealizado.setKmRecorrido(kmRecorridos);
        trabajoRealizado.setGanancia(gananciaRepartidor);

        trabajoRepository.save(trabajoRealizado); // Guardar el trabajo realizado
        return "Pedido guardado con éxito";
    }


    @Transactional
    public UsuarioDTO buscarRepartidor(String correo) {
     
        // 1. Buscar al usuario comprador
        Usuario comprador = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
         
        // 2. Buscar el estado "EN PROCESO"
        Estado estadoEnProceso = estadoRepository.findByEstado("En Proceso")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado 'EN PROCESO' no encontrado"));
       
      // 3. Buscar el último pedido del comprador con estado "EN PROCESO"
    Pedido pedido = pedidoRepository.findTopByCompradorAndEstadoOrderByFechaPedidoDesc(comprador, estadoEnProceso)
    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay pedidos en proceso"));

          
        // 4. Buscar el estado "DISPONIBLE"
        Estado estadoDisponible = estadoRepository.findByEstado("DISPONIBLE")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado 'DISPONIBLE' no encontrado"));
            System.out.println("Estado: "+estadoDisponible.getEstado());
       
            // 5. Buscar el repartidor con el estado "DISPONIBLE"
    // 1. Obtener todos los usuarios
List<Usuario> todosLosUsuarios = usuarioRepository.findAll();

// 2. Recorrer la lista de usuarios para encontrar el primero que esté disponible
Usuario repartidor = null;
for (Usuario usuario : todosLosUsuarios) {
    if (usuario.getEstado().getEstado().equals("DISPONIBLE")) {
        repartidor = usuario;
        break; // Terminar el loop al encontrar el primer repartidor disponible
    }
}

// 3. Verificar si encontramos un repartidor disponible
if (repartidor == null) {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay repartidores disponibles");
}

System.out.println("Repartidor disponible: " + repartidor.getNombre());
         
        // 6. Crear el estado 'PENDIENTE'
        Estado estado = new Estado();
        estado.setEstado("PENDIENTE");
    
        // 7. Asignar repartidor al pedido
        pedido.setRepartidor(repartidor);
        pedido.setEstado(estado);  // Cambiar el estado a 'PENDIENTE'
        System.out.println("Pedido: "+pedido.getIdPedido());
        
      Pedido pedidoActualizado=  pedidoRepository.save(pedido); // Guardar el pedido actualizado

      System.out.println("Pedido Actualizado: "+pedidoActualizado.getRepartidor().getNombre());
        // 8. Crear el DTO de usuario para retornar
        UsuarioDTO usuarioDTO =this.modelMapper.map(repartidor,UsuarioDTO.class);
        System.out.println("Aqui se queda" + usuarioDTO.getNombre());
        // 9. Retornar los detalles del usuario con los datos del pedido actualizado
        return usuarioDTO;
    }
    
    
    


}
