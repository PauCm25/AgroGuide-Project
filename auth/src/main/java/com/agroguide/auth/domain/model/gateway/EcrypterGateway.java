package com.agroguide.auth.domain.model.gateway;

public interface EcrypterGateway {
    String encrypt(String password);
    Boolean checkPassword(String passUser, String passBD);
}
