package com.emeka.delivery.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emeka.delivery.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
      Optional<Rol> findByRol(String rol);
}
