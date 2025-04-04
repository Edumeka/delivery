package com.emeka.delivery.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Lugar;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Integer>{
    List<Lugar> findByTipo(String tipo);

    // Método para obtener departamentos (o cualquier lugar) por idLugarSuperior
    List<Lugar> findByLugarSuperiorAndTipo(Lugar lugarSuperior, String tipo);


}
