package com.emeka.delivery.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.PedidoProducto;
import com.emeka.delivery.models.PedidoProductoId;

@Repository
public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, PedidoProductoId> {
    
    
}
