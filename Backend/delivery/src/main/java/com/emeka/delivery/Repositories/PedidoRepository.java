package com.emeka.delivery.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
}
