package com.emeka.delivery.DTO;


import com.emeka.delivery.models.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;

    
    @JsonIgnore
    private String contrasenia;
    private String dni;
    private Rol rol;
    private EstadoDTO estado;
    private DireccionDTO ubicacionRepartidor;
}
