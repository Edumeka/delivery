package com.emeka.delivery.DTO;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO {
     private int idRol;

    private String rol;
    
   
    private UsuarioDTO usuario;
}
