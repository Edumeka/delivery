package com.emeka.delivery.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
}
