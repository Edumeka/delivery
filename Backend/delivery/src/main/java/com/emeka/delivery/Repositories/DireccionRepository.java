package com.emeka.delivery.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emeka.delivery.models.Direccion;
import com.emeka.delivery.models.Empresa;
import com.emeka.delivery.models.Usuario;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    // Método para verificar si ya existe una dirección para un usuario
    Direccion findByUsuarioAndDireccion(Usuario usuario, String direccion);

    Direccion findByEmpresaAndDireccion(Empresa empresa, String direccion);

    
   
    // Método para obtener todas las direcciones de un usuario
    List<Direccion> findByUsuario(Usuario usuario);

    
    Optional<Direccion> findByEmpresa(Empresa empresa);

    
    // Método para obtener la primera dirección de un usuario (si existe)
    Optional<Direccion> findFirstByUsuario(Usuario usuario);


}
