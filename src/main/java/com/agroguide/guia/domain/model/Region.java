package com.agroguide.guia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor //CREACION DEL CONSTRUCTOR
@NoArgsConstructor
@Setter //MODIFICA ATRIBUTO
@Getter //OBTENER ATRIBUTO
public class Region {

    private Long idRegion;
    private String nombreRegion;
    private String departamento;

}
