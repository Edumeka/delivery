package com.emeka.delivery.DTO;


import com.emeka.delivery.config.PointDeserializer;
import com.emeka.delivery.config.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
      private int idDireccion;
    
    private String direccion;
    private String descripcion;

    @JsonSerialize(using = PointSerializer.class)
    @JsonDeserialize(using = PointDeserializer.class)
    private org.locationtech.jts.geom.Point ubicacion;

    private LugarDTO lugar;

    private UsuarioDTO usuario;


    private EmpresaDTO empresa;
}
