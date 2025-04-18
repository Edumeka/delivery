package com.emeka.delivery.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.PedidoDTO;
import com.emeka.delivery.DTO.UsuarioDTO;
import com.emeka.delivery.Repositories.CarritoRepository;
import com.emeka.delivery.Repositories.EstadoRepository;
import com.emeka.delivery.Repositories.PagoRepository;
import com.emeka.delivery.Repositories.PedidoProductoRepository;
import com.emeka.delivery.Repositories.PedidoRepository;
import com.emeka.delivery.Repositories.ProductoMasVendidoRepository;
import com.emeka.delivery.Repositories.TrabajoRealizadoRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.Carrito;
import com.emeka.delivery.models.Empresa;
import com.emeka.delivery.models.Estado;
import com.emeka.delivery.models.Pago;
import com.emeka.delivery.models.Pedido;
import com.emeka.delivery.models.PedidoProducto;
import com.emeka.delivery.models.PedidoProductoId;
import com.emeka.delivery.models.Producto;
import com.emeka.delivery.models.ProductoMasVendido;
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
private ProductoMasVendidoRepository productoMasVendidoRepository;
@Autowired
private PagoRepository pagoRepository;
        @Autowired
        private ModelMapper modelMapper;

        @Transactional
        public String guardarPedido(String correo) {
                // 1. Buscar al usuario comprador
                Usuario comprador = usuarioRepository.findByCorreo(correo)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Usuario no encontrado"));

                // 2. Obtener los items del carrito del usuario
                List<Carrito> carritos = carritoRepository.findByUsuario(comprador);

                if (carritos.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito está vacío");
                }

                // 3. Validar que todos los productos sean de la misma empresa
                Empresa empresaDelPedido = carritos.get(0).getProducto().getEmpresa();

                boolean mismaEmpresa = carritos.stream()
                                .allMatch(c -> c.getProducto().getEmpresa().getIdEmpresa() == empresaDelPedido
                                                .getIdEmpresa());

                if (!mismaEmpresa) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Todos los productos deben ser de la misma empresa");
                }

                // 4. Crear un nuevo pedido
                Pedido pedido = new Pedido();
                pedido.setFechaPedido(LocalDateTime.now());
                pedido.setComprador(comprador);
                pedido.setEmpresa(empresaDelPedido);
                pedido.setPago(null);
                pedido.setMontoTotalDeProductos(
                                carritos.stream().mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                                                .sum());

                // Buscar el estado "EN PROCESO" o crearlo si no existe
                Estado estadoEnProceso = estadoRepository.findByEstado("EN PROCESO")
                                .orElseGet(() -> {
                                        Estado nuevoEstado = new Estado();
                                        nuevoEstado.setEstado("EN PROCESO");
                                        return estadoRepository.save(nuevoEstado);
                                });

                pedido.setEstado(estadoEnProceso); // Asignar el estado al pedido

                // 5. Guardar el pedido en base de datos
                Pedido pedidoGuardado = pedidoRepository.save(pedido);

                // 6. Mapear cada carrito a un detalle de pedido
                for (Carrito item : carritos) {
                        PedidoProducto detalle = new PedidoProducto();
                        // Crear la clave compuesta
                        PedidoProductoId id = new PedidoProductoId(pedidoGuardado.getIdPedido(),
                                        item.getProducto().getIdProducto());
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
public String asignarPago(Pago pago, Usuario comprador, double kmRecorridos, double costoEnvio,
                          double gananciaRepartidor) {

 
    // 1. Obtener los items del carrito del usuario
    List<Carrito> carritos = carritoRepository.findByUsuario(comprador);

    if (carritos.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito está vacío");
    }

    // 2. Validar que todos los productos sean de la misma empresa
    Empresa empresaDelPedido = carritos.get(0).getProducto().getEmpresa();

    boolean mismaEmpresa = carritos.stream()
            .allMatch(c -> c.getProducto().getEmpresa().getIdEmpresa() == empresaDelPedido.getIdEmpresa());

    if (!mismaEmpresa) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Todos los productos deben ser de la misma empresa");
    }

    // 3. Crear un nuevo pedido
    Pedido pedido = new Pedido();
    pedido.setFechaPedido(LocalDateTime.now());
    pedido.setComprador(comprador);
    pedido.setEmpresa(empresaDelPedido);
    pedido.setPago(pago);
    pedido.setCostoEnvioTotal(costoEnvio + gananciaRepartidor);
    pedido.setMontoTotalDeProductos(
            carritos.stream().mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario()).sum());

    // Buscar el estado "EN PROCESO" o crearlo si no existe
    Estado estadoEnProceso = estadoRepository.findByEstado("EN PROCESO")
            .orElseGet(() -> {
                Estado nuevoEstado = new Estado();
                nuevoEstado.setEstado("EN PROCESO");
                return estadoRepository.save(nuevoEstado);
            });

    pedido.setEstado(estadoEnProceso); // Asignar el estado al pedido

    // 4. Guardar el pedido en base de datos
    Pedido pedidoGuardado = pedidoRepository.save(pedido);

    // 5. Mapear cada carrito a un detalle de pedido
    for (Carrito item : carritos) {
        PedidoProducto detalle = new PedidoProducto();
        PedidoProductoId id = new PedidoProductoId(pedidoGuardado.getIdPedido(),
                item.getProducto().getIdProducto());
        detalle.setId(id);
        detalle.setPedido(pedidoGuardado);
        detalle.setProducto(item.getProducto());
        detalle.setCantidad(item.getCantidad());
        detalle.setPrecioUnitario(item.getPrecioUnitario());
        detalle.setSubTotal(item.getCantidad() * item.getPrecioUnitario());
        detallePedidoRepository.save(detalle);
    }

    // 6. Actualizar productos más vendidos antes de limpiar el carrito
    for (Carrito item : carritos) {
        Producto producto = item.getProducto();
        int cantidadVendida = item.getCantidad();
        double ingreso = cantidadVendida * item.getPrecioUnitario();

        Optional<ProductoMasVendido> existente = productoMasVendidoRepository.findByProducto(producto);

        if (existente.isPresent()) {
            ProductoMasVendido pmv = existente.get();
            pmv.setCantidadVendida(pmv.getCantidadVendida() + cantidadVendida);
            pmv.setTotalIngresos(pmv.getTotalIngresos() + ingreso);
            pmv.setUltimaFechaVenta(LocalDateTime.now());
            productoMasVendidoRepository.save(pmv);
        } else {
            ProductoMasVendido nuevo = new ProductoMasVendido();
            nuevo.setProducto(producto);
            nuevo.setCantidadVendida(cantidadVendida);
            nuevo.setTotalIngresos(ingreso);
            nuevo.setUltimaFechaVenta(LocalDateTime.now());
            productoMasVendidoRepository.save(nuevo);
        }
    }

    // 7. Limpiar el carrito del usuario
    carritoRepository.deleteAll(carritos);

    // 8. Guardar el trabajo realizado
    TrabajoRealizado trabajoRealizado = new TrabajoRealizado();
    trabajoRealizado.setPedido(pedidoGuardado);
    trabajoRealizado.setFecha(LocalDateTime.now());
    trabajoRealizado.setKmRecorrido(kmRecorridos);
    trabajoRealizado.setGanancia(gananciaRepartidor);
    trabajoRepository.save(trabajoRealizado);

    return "Pedido guardado con éxito";
}


        @Transactional
        public UsuarioDTO buscarRepartidor(String correo) {

                // 1. Buscar al usuario comprador
                Usuario comprador = usuarioRepository.findByCorreo(correo)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Usuario no encontrado"));

                // 2. Buscar el estado "EN PROCESO"
                Estado estadoEnProceso = estadoRepository.findByEstado("EN PROCESO")
                                .orElseThrow(
                                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                "Estado 'EN PROCESO' no encontrado"));

                // 3. Buscar el último pedido del comprador con estado "EN PROCESO"
                Pedido pedido = pedidoRepository
                                .findTopByCompradorAndEstadoOrderByFechaPedidoDesc(comprador, estadoEnProceso)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "No hay pedidos EN PROCESO"));

                UsuarioDTO usuarioDTO = null;

                if (pedido.getRepartidor() == null) {
                        // 4. Buscar el estado "DISPONIBLE"
                        Estado estadoDisponible = estadoRepository.findByEstado("DISPONIBLE")
                                        .orElseThrow(
                                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                        "Estado 'DISPONIBLE' no encontrado"));

                        System.out.println("Estado: " + estadoDisponible.getEstado());

                        // 5. Buscar el repartidor con el estado "DISPONIBLE"
                        // 1. Obtener todos los usuarios
                        List<Usuario> todosLosUsuarios = usuarioRepository.findAll();

                        // 2. Recorrer la lista de usuarios para encontrar el primero que esté
                        // disponible
                        Usuario repartidor = null;
                        for (Usuario usuario : todosLosUsuarios) {
                                if (usuario.getEstado().getEstado().equals("DISPONIBLE")) {
                                        repartidor = usuario;
                                        break; // Terminar el loop al encontrar el primer repartidor disponible
                                }
                        }

                        // 3. Verificar si encontramos un repartidor disponible
                        if (repartidor == null) {
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                "No hay repartidores disponibles");
                        }

                        System.out.println("Repartidor disponible: " + repartidor.getNombre());

                        // 6. Crear el estado 'PENDIENTE'
                        Optional<Estado> estadoPendiente = Optional
                                        .ofNullable(estadoRepository.findByEstado("PENDIENTE")
                                                        .orElseGet(() -> {
                                                                Estado nuevoEstado = new Estado();
                                                                nuevoEstado.setEstado("PENDIENTE");
                                                                return estadoRepository.save(nuevoEstado);

                                                        }));
                        Optional<Estado> estadoOcupado = Optional.ofNullable(estadoRepository.findByEstado("OCUPADO")
                                        .orElseGet(() -> {
                                                Estado nuevoEstado = new Estado();
                                                nuevoEstado.setEstado("OCUPADO");
                                                return estadoRepository.save(nuevoEstado);
                                        }));

                        // 7. Cambiar el estado del repartidor a "OCUPADO" y actualizar en la base de
                        // datos
                        repartidor.setEstado(estadoOcupado.get());
                        usuarioRepository.save(repartidor);

                        // 8. Asignar repartidor al pedido y cambiar el estado del pedido a "PENDIENTE"
                        pedido.setRepartidor(repartidor);
                        pedido.setEstado(estadoPendiente.get()); // Cambiar el estado a 'PENDIENTE'

                        pedidoRepository.save(pedido); // Guardar el pedido actualizado

                        // Busco en la tabla trabajosRealizados que serian el historial de trabajos del
                        // Repartidor
                        TrabajoRealizado trabajoRealizado = trabajoRepository.findByPedido(pedido);

                        // Le asigno el repartidor al historial
                        trabajoRealizado.setRepartidor(repartidor);
                        // Y lo guardo
                        trabajoRepository.save(trabajoRealizado);

                        // 9. Crear el DTO de usuario para retornar
                        usuarioDTO = this.modelMapper.map(repartidor, UsuarioDTO.class);
                } else {
                        // Si el pedido ya tiene un repartidor, se mapea el repartidor asignado
                        usuarioDTO = this.modelMapper.map(pedido.getRepartidor(), UsuarioDTO.class);
                }

                // 10. Retornar los detalles del usuario con los datos del pedido actualizado
                return usuarioDTO;
        }

        public String actualizarEstadoDelPedido(String correo) {
                // 1. Buscar al usuario comprador
                Usuario comprador = usuarioRepository.findByCorreo(correo)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Usuario no encontrado"));

                // 2. Buscar el estado "EN PROCESO"
                Estado estadoPendiente = estadoRepository.findByEstado("PENDIENTE")
                                .orElseThrow(
                                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                "Estado 'PENDIENTE' no encontrado"));

                // 3. Buscar el último pedido del comprador con estado "EN PROCESO"
                Pedido pedido = pedidoRepository
                                .findTopByCompradorAndEstadoOrderByFechaPedidoDesc(comprador, estadoPendiente)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "No hay pedidos PENDIENTE"));

                Optional<Estado> estadoFinalizado = Optional.ofNullable(estadoRepository.findByEstado("FINALIZADO")
                                .orElseGet(() -> {
                                        Estado nuevoEstado = new Estado();
                                        nuevoEstado.setEstado("FINALIZADO");
                                        return estadoRepository.save(nuevoEstado); // Guarda el nuevo estado en la base
                                                                                   // de datos
                                }));
                pedido.setEstado(estadoFinalizado.get());
                pedido.setFechaFinal(LocalDateTime.now());

                pedidoRepository.save(pedido);
                        // . Buscar el estado "DISPONIBLE"
                        Estado estadoDisponible = estadoRepository.findByEstado("DISPONIBLE")
                        .orElseThrow(
                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                        "Estado 'DISPONIBLE' no encontrado"));
                Usuario repartidor = pedido.getRepartidor();

                repartidor.setEstado(estadoDisponible);

                usuarioRepository.save(repartidor);

                return "Se ha actualizado el pedido";
        }

        public List<PedidoDTO> obtenerPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        // Asegúrate de que tu ModelMapper esté configurado correctamente
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                                            .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                                            .collect(Collectors.toList());

        return pedidosDTO;
    }



public int pedidosDelDia() {
        LocalDateTime inicioDelDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finDelDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return (int) pedidoRepository.findAll().stream()
                        .filter(pedido -> pedido.getFechaPedido().isAfter(inicioDelDia) && pedido.getFechaPedido().isBefore(finDelDia))
                        .count();
}

public double totalVendido() {
        List<Pago> pagos = pagoRepository.findAll();
       
                return pagos.stream()
                            .mapToDouble(Pago::getTotalFactura)
                            .sum();           
            
        
}
public Map<String, Integer> reporteEstadoPedido() {
        // Buscar los objetos Estado en la base de datos
        Optional<Estado> estadoFinalizado = estadoRepository.findByEstado("FINALIZADO");
        Optional<Estado> estadoPendiente = estadoRepository.findByEstado("PENDIENTE");
        Optional<Estado> estadoEnProceso = estadoRepository.findByEstado("EN PROCESO");

        // Contamos los pedidos por estado
        int finalizados = pedidoRepository.countByEstado(estadoFinalizado.get());
        int pendientes = pedidoRepository.countByEstado(estadoPendiente.get());
        int enProceso = pedidoRepository.countByEstado(estadoEnProceso.get());

        return Map.of(
                "finalizados", finalizados,
                "pendientes", pendientes,
                "enProceso", enProceso
        );
    }
}
