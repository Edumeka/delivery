package com.emeka.delivery.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Estado;
import com.emeka.delivery.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {
    Optional<Usuario> findByCorreo(String correo);
    Boolean existsByCorreo(String correo);
    Optional<Usuario> findByEstado(Estado estado); // Esto buscaría un usuario por estado
}