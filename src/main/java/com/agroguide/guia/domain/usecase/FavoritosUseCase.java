package com.agroguide.guia.domain.usecase;


import com.agroguide.guia.domain.model.Favoritos;
import com.agroguide.guia.domain.model.Guia;
import com.agroguide.guia.domain.model.gateway.FavoritosGateway;
import com.agroguide.guia.domain.model.gateway.GuiaGateway;
import com.agroguide.guia.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import com.agroguide.guia.domain.exception.*;

import java.util.ArrayList;

@RequiredArgsConstructor
public class FavoritosUseCase {

    //Inyectar caso de uso de Guia

    private final FavoritosGateway favoritosGateway;
    private final GuiaGateway guiaGateway;
    private final UsuarioGateway usuarioGateway;
    private final GuiaUseCase guiaUseCase;

    public Favoritos agregarAFavoritos (Long usuarioId, Long idGuia) {
        //verificar que el usuario exista
        if (!usuarioGateway.usuarioExiste(usuarioId)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }

        //verificar que la guía que se va a agregar exista
        Guia guia = guiaGateway.consultarPorId(idGuia);
        if (guia == null || guia.getIdGuia() == null) {
            throw new GuiaNoExisteException("Guia no encontrada - AgregarAFavoritos");
        }

        //Crear el obj favoritos
        Favoritos favoritos = new Favoritos();
        favoritos.setIdGuia(idGuia);
        favoritos.setIdUsuario(usuarioId);
        favoritos.setTituloGuia(guia.getTitulo());
        favoritos.setAutorGuia(guia.getNombreAutor());

        //guardar el objeto
        return favoritosGateway.AgregarFavs(favoritos);
    }


}
