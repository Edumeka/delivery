package com.emeka.delivery.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Carrito;
import com.emeka.delivery.models.Producto;
import com.emeka.delivery.models.Usuario;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByUsuarioAndProducto(Usuario usuario, Producto producto);

     List<Carrito> findByUsuario(Usuario usuario); // Método para obtener el carrito de un usuario específico

}
