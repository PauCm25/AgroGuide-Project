package com.agroguide.guia.infraestructure.driver_adapter.external_repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class UsuarioGatewayImpl {

    private final RestTemplate restTemplate;

    @Override
    public boolean usuarioExiste(Long usuarioId){
        try {
            restTemplate.getForEntity("http://localhost:8080/api/agroguide/usuario "+usuarioId, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception errorMensaje) {
            throw new RuntimeException("Error al consultar el microservicio de Auth", errorMensaje);
        }
    }
}
