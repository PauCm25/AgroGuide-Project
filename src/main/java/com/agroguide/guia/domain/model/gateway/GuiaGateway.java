package com.agroguide.guia.domain.model.gateway;

import com.agroguide.guia.domain.model.Guia;

import java.util.List;

public interface GuiaGateway {

    Guia crear (Guia guia);
    void eliminarPorId(Long idGuia);
    Guia consultarPorId(Long idGuia);
    Guia actualizarPorId(Guia guia);
    List<Guia> ListarGuias(int page, int size);
    Guia actualizarEstadoGuia(Guia guia);
}
