package com.agroguide.auth.domain.model.gateway;

public interface EncrypterGateway{
    String encrypt (String password);//encriptacion

    Boolean checkPass  (String passUser, String passBD);//verificacion que esten iguales las contraseñas en la BD

}