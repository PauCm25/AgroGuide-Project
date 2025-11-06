package com.agroguide.auth.infraestructure.mapper;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.infraestructure.driver_adapters.UsuarioData;
import org.springframework.stereotype.Component;

@Component
public class MapperUsuario {
    public Usuario toUsuario(UsuarioData usuarioData) {
        return new Usuario(
                usuarioData.getId(),
                usuarioData.getNombre(),
                usuarioData.getEmail(),
                usuarioData.getPassword(),
                usuarioData.getTipoUsuario(),
                usuarioData.getUbicacion(),
                usuarioData.getEdad()
        );
    }
    public UsuarioData toData(Usuario usuario){
        return new UsuarioData(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getTipoUsuario(),
                usuario.getUbicacion(),
                usuario.getEdad());
    }
}
