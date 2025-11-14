package com.agroguide.guia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

//SIEMPRE DEBEN IR ESTAS ETIQUETAS
@AllArgsConstructor //CREACION DEL CONSTRUCTOR
@NoArgsConstructor
@Setter //MODIFICA ATRIBUTO
@Getter //OBTENER ATRIBUTO
public class Guia {
    //Atributos-------------------------------------------------
    private Long idGuia; //automatica

    //EXCLUSIVA PARA ROL TÉCNICO Y ADMIN
    private Long idTecnico;
    private String titulo;
    private String descripcion;
    private String fechaPublicacion;
    private String nombreAutor; // Esto se debe tomar automaticamente del usuario técnico que lo esté creando
    private String etiquetas;
    private String tipoCultivo;
    private String region;
    private List<Favoritos> favs;


    //SOLO ADMINISTRADORES Y TÉCNICOS
    private String estadoGuia; //Esto va a ser cuando ya se agreguen los atributos anteriores.
    //Los estados van a ser PENDIENTE, APROBADO y RECHAZADO.
    //Solo cuando estén  aprobados,


    //Además, los usuarios agricultores no pueden tener acceso al CRUD de la guía.

}

