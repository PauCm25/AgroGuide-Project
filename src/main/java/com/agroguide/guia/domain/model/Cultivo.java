package com.agroguide.guia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor //CREACION DEL CONSTRUCTOR
@NoArgsConstructor
@Setter //MODIFICA ATRIBUTO
@Getter //OBTENER ATRIBUTO
public class Cultivo {

    private Long idCultivo;
    private String nombreCultivo;
    private String tipoSuelo;
    private String climaRecomendado;

}
