package com.agroguide.guia.infraestructure.mapper;

import com.agroguide.guia.domain.model.Favoritos;
import com.agroguide.guia.infraestructure.driver_adapter.jpa_repository.favoritos.FavoritosData;
import org.springframework.stereotype.Component;

@Component
public class MapperFavoritos {

    public Favoritos toFavs(FavoritosData data) {
        Favoritos fav = new Favoritos();
        fav.getIdFav(data.getIdFav),

    }
}
