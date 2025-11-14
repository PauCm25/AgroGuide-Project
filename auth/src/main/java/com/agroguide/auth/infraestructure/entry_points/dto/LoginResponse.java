package com.agroguide.auth.infraestructure.entry_points.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String mensaje;
    private Long usuarioId;
    private String nombre;
    private String tipoUsuario;
}
