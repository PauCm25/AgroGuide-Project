package com.agroguide.auth.infraestructure.driver_adapters;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioData {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String tipoUsuario;//agricultor, desarrollador,etc
    private String ubicacion;
    private Integer edad;
}
