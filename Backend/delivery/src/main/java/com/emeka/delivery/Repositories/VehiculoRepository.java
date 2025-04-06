package com.emeka.delivery.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Usuario;
import com.emeka.delivery.models.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer>{
    Vehiculo findByRepartidor(Usuario repartidor);
}
