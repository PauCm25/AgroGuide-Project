package com.agroguide.auth.infraestructure.entry_points.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String tipoUsuario;//agricultor, desarrollador,etc
    private String ubicacion;
    private Integer edad;
}
