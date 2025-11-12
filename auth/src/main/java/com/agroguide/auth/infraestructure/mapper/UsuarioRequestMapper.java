package com.agroguide.auth.infraestructure.mapper;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.infraestructure.entry_points.dto.UsuarioRequest;
import org.springframework.stereotype.Component;

@Component
public class  UsuarioRequestMapper {
    public Usuario toUsuario(UsuarioRequest usuarioRequest) {
        return new Usuario(
                usuarioRequest.getId(),
                usuarioRequest.getNombre(),
                usuarioRequest.getEmail(),
                usuarioRequest.getPassword(),
                usuarioRequest.getTipoUsuario(),
                usuarioRequest.getUbicacion(),
                usuarioRequest.getEdad()
        );
    }
}

