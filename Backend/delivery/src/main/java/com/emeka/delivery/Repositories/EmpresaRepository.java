package com.emeka.delivery.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByEmpresa(String nombre);

}
