package com.agroguide.guia.domain.usecase;

import com.agroguide.guia.domain.model.Guia;
import com.agroguide.guia.domain.model.gateway.GuiaGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GuiaUseCase {

    private final GuiaGateway guiaGateway;

    public Guia crearGuia(Guia guia){
        //Condicional para hacer que el nombre y el precio sean obligatorios en el producto
        //si ambos son nulos, no se guarda el producto
        if(guia.getTitulo() == null && guia.getTipoCultivo() == null
                && guia.getDescripcion() == null
                && guia.getRegion() == null && guia.getEtiquetas() == null){
            throw new NullPointerException("Ingrese atributos correctamente - crearGuia");
        }

        return guiaGateway.crear(guia); //Si esta bien, guarda el producto
    }

    public void eliminarGuia(Long id){
        //Elimina la guia por el ID
        //Retorna un vacío
        //Lanza excepcion en caso de haber error
        try {
            guiaGateway.eliminarPorId(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al eliminar la guía. No existe");
        }
    }

    public Guia consultarGuia(Long id){
        //Busca una guia por su ID
        //Si no lo encuentra, retorna una guia vacía
        //Si lo encuentra, retorna la guía entera
        try{
            return guiaGateway.consultarPorId(id);
        } catch (Exception e){
            System.out.println("Error al consultar la guía");

            //crear un producto vacío para retornarlo
            Guia guiaVacia = new Guia();
            return guiaVacia;
        }
    }

    public Guia actualizarGuia(Guia guia){
        //Valida primero que el ID no sea nulo. Si lo es, muestra una excepcion
        //Si no es nulo, actualiza el producto existente en la BD
        //retorna el producto actualizado
        if(guia.getIdGuia() == null){
            throw new NullPointerException("Revise que el ID de la guía exista - actualizarGuia");
        }
        return guiaGateway.actualizarPorId(guia);
    }

    public List<Guia> consultarGuias(int page, int size) //No necesita parametros, porque retorna todos los productos
    {
        //Obtiene todos los productos existentes en la BD
        //Retorna una lista
        //si hay error en la consulta, atrapa la excepcion
        try {
            return guiaGateway.ListarGuias(page, size);
        } catch(Exception e) {
            throw new IllegalArgumentException("Error al consultar las guías existentes");
        }
    }

    public Guia actualizarEstGuia (Guia guia){
        if (guia.getIdGuia() == null){
            throw new NullPointerException("No existe la guía");
        }
        return guiaGateway.actualizarEstadoGuia(guia);
    }

}
