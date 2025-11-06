package com.agroguide.auth.application.config;

import com.agroguide.auth.domain.model.gateway.UsuarioGateway;
import com.agroguide.auth.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;

public class UseCaseConfig {
@Bean
    public UsuarioUseCase usuarioUseCase(UsuarioGateway usuarioGateway){
    return new UsuarioUseCase( usuarioGateway, encrypterGateway);
}
}
