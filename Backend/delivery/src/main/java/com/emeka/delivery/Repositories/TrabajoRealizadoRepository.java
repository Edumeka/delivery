package com.emeka.delivery.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Pedido;
import com.emeka.delivery.models.TrabajoRealizado;
import com.emeka.delivery.models.Usuario;

@Repository
public interface TrabajoRealizadoRepository extends JpaRepository<TrabajoRealizado, Integer> {
    TrabajoRealizado findByPedido(Pedido pedido);
    List<TrabajoRealizado> findByRepartidor(Usuario repartidor);
}
