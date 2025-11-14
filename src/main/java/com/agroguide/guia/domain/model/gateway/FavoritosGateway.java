package com.agroguide.guia.domain.model.gateway;

import com.agroguide.guia.domain.model.Favoritos;
import com.agroguide.guia.domain.model.Guia;
import java.util.List;

public interface FavoritosGateway {

    Favoritos AgregarFavs(Favoritos favoritos);
    void EliminarDeFav(Long idGuia, Long usuarioId);
    List<Favoritos> ListarGuias(int page, int size);
}
