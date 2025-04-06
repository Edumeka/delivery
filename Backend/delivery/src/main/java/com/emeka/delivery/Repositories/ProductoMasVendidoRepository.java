package com.emeka.delivery.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Producto;
import com.emeka.delivery.models.ProductoMasVendido;

@Repository
public interface ProductoMasVendidoRepository extends JpaRepository<ProductoMasVendido, Integer> {
    Optional<ProductoMasVendido> findByProducto(Producto producto);
}
