package com.emeka.delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
     private int idEmpresa;

    private String empresa;
   
    private String rtn;
    private String imagen;
  
    private double costoEnvio;
    
    private UsuarioDTO administradorEmpresa;
}
