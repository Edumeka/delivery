package com.emeka.delivery.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.TrabajoRealizado;

@Repository
public interface TrabajoRealizadoRepository extends JpaRepository<TrabajoRealizado, Integer> {
    
}
