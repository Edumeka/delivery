package com.emeka.delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String nombre;
    private String contrasenia;
    private String correo;
    private String dni;
    private String apellido;
    private String telefono;
    
}
