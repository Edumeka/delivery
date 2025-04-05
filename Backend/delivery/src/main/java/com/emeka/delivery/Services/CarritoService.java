package com.emeka.delivery.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.CarritoDTO;
import com.emeka.delivery.DTO.ProductoDTO;
import com.emeka.delivery.Repositories.CarritoRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.models.Carrito;
import com.emeka.delivery.models.Producto;
import com.emeka.delivery.models.Usuario;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

/*
 * Método para guardar un producto en el carrito del usuario.
 * Si el producto ya existe en el carrito, se actualiza la cantidad.
 * Si no existe, se crea un nuevo registro en el carrito.
 * @param productoDTO Objeto que contiene la información del producto a agregar al carrito.
 * @param correo Correo del usuario que está agregando el producto al carrito.
 * @param cantidad Cantidad del producto a agregar al carrito.
 * @return Mensaje indicando si el producto fue agregado o si la cantidad fue actualizada.
 */
   public String guardarCarrito(ProductoDTO productoDTO, String correo, int cantidad) {
    Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

    if (usuario.isPresent()) {
        Producto producto = modelMapper.map(productoDTO, Producto.class); // Convertir DTO a entidad
        Usuario usuarioEntidad = usuario.get();

        // Buscar si ya existe el producto en el carrito del usuario
        Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioAndProducto(usuarioEntidad, producto);

        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();
            int nuevaCantidad = carrito.getCantidad() + cantidad;
            carrito.setCantidad(nuevaCantidad);
            carrito.setSubTotal(nuevaCantidad * carrito.getPrecioUnitario());
            carrito.setFechaCreacion(LocalDateTime.now());

            carritoRepository.save(carrito);
            return "Cantidad del producto actualizada en el carrito";
        } else {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuarioEntidad);
            carrito.setProducto(producto);
            carrito.setCantidad(cantidad);
            carrito.setPrecioUnitario(producto.getPrecio());
            carrito.setSubTotal(cantidad * producto.getPrecio());
            carrito.setFechaCreacion(LocalDateTime.now());

            carritoRepository.save(carrito);
            return "Producto agregado al carrito con éxito";
        }

    } else {
        return "Usuario no encontrado";
    }
}

public List<CarritoDTO> verCarrito(String correo) {
    System.out.println("correo: " + correo);
    Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

    if (usuario.isPresent()) {
        Usuario usuarioEntidad = usuario.get();
        List<Carrito> carritos = carritoRepository.findByUsuario(usuarioEntidad);
        return carritos.stream()
                .map(carrito -> modelMapper.map(carrito, CarritoDTO.class))
                .collect(Collectors.toList());
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, null);
    }
}

/*
 * Método para eliminar un carrito por su ID.
 * Si el carrito existe, se elimina; si no, se lanza una excepción.
 * @param idCarrito ID del carrito a eliminar.
 * @return Mensaje indicando si el carrito fue eliminado o no encontrado.
 */
public String eliminarCarrito(int idCarrito) {
    Optional<Carrito> carrito = carritoRepository.findById(idCarrito);
    if (carrito.isPresent()) {
        carritoRepository.delete(carrito.get());
        return "Carrito eliminado con éxito";
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
    }
}

}