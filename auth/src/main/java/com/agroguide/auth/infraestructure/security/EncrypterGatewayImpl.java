package com.agroguide.auth.infraestructure.security;

import com.agroguide.auth.domain.model.gateway.EncrypterGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncrypterGatewayImpl implements EncrypterGateway {

    //dependencia security
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encrypt(String password) {
        return encoder.encode(password);
    }

    //encoder.matches(pertenece a BCryptPasswordEncoder)=comparar una contraseña en texto plano con una encriptada (hash).
    @Override
    public Boolean checkPass(String passUser, String passBD) {
        return encoder.matches(passUser, passBD);
    }

}
