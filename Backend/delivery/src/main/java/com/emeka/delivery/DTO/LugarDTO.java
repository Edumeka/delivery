package com.emeka.delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LugarDTO {
    private int idLugar;

    private String lugar;
    private String tipo;

    private LugarDTO LugarSuperior;
}
