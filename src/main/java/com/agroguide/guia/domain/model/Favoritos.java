package com.agroguide.guia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//SIEMPRE DEBEN IR ESTAS ETIQUETAS
@AllArgsConstructor //CREACION DEL CONSTRUCTOR
@NoArgsConstructor
@Setter //MODIFICA ATRIBUTO
@Getter //OBTENER ATRIBUTO
public class Favoritos {
    //Atributos-------------------------------------------------
    private Long idFav;
    private Long idUsuario;
    private Long idGuia;
    private String tituloGuia;
    private String autorGuia;

}
