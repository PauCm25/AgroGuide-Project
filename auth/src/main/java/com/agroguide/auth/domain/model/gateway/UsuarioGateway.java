package com.agroguide.auth.domain.model.gateway;

import com.agroguide.auth.domain.model.Usuario;

public interface UsuarioGateway {

    Usuario guardar (Usuario usuario);
    Usuario buscarPorEmail(String email);
    void elimnarPorID(long id);
    Usuario buscarPorID(long id);
    Usuario actualizarUsuario (Usuario usuario);
}
