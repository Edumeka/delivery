package com.emeka.delivery.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emeka.delivery.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByEstado(String estado);
}