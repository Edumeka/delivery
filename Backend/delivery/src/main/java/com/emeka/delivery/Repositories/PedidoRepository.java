package com.emeka.delivery.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Estado;
import com.emeka.delivery.models.Pedido;
import com.emeka.delivery.models.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
     // Buscar el último pedido del comprador con estado "EN PROCESO"
     Optional<Pedido> findTopByCompradorAndEstadoOrderByFechaPedidoDesc(Usuario comprador, Estado estado);
}
