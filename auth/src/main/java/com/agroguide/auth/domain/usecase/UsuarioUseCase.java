package com.agroguide.auth.domain.usecase;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.domain.model.gateway.EncrypterGateway;
import com.agroguide.auth.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j //inyecta log por medio de Loombok
public class UsuarioUseCase {
    private final UsuarioGateway usuarioGateway;
    private final EncrypterGateway encrypterGateway;

    public Usuario guardarUsuario (Usuario usuario) {
        //si da error, en los logs aparece ese mensaje
        if (usuario.getEmail() == null && usuario.getPassword() == null) {
            //Arrojar excepciones
            throw new NullPointerException("Datos Invalidos - guardarUsuario");
        }
        //VALIDAR EL FORMATO DEL CORREO
        if (!usuario.getEmail().contains("@")) {
            log.warn("Email invalido");
            return new Usuario();
        }
        //VALIDA LA EDAD NO SEA MENOS DE 15
        if(usuario.getEdad()==null||usuario.getEdad()<16){
            throw new IllegalArgumentException("Debe ser mayor de 15 años para registrarse");
        }
        String passwordEncrypt = encrypterGateway.encrypt(usuario.getPassword());
        usuario.setPassword(passwordEncrypt);
        try{
            return usuarioGateway.guardar(usuario);
        }catch(Exception e){
            log.error("Error al guardar usuario", e.getMessage());
            return new Usuario();//lo devuelve vacio si llega a fallar
        }

    }
    public String loginUsuario (String email, String password) {
        Usuario usuarioLogueo = usuarioGateway.buscarPorEmail(email);
        if (usuarioLogueo.getEmail()==null || usuarioLogueo.getPassword()==null) {
            return "Usuario no encontrado";

        }
        if (encrypterGateway.checkPass(password, usuarioLogueo.getPassword())) {
            return "Credenciales Válidas";
        } else  {
            return "Credenciales Inválidas";
        }
    }
    public Usuario actualizarUsuario (Usuario usuario) {
        if (usuario.getId()==null){
            throw new IllegalArgumentException("Es necesario el ID para actualizar");
        }
        usuario.setPassword(encrypterGateway.encrypt(usuario.getPassword()));
        return usuarioGateway.actualizarUsuario(usuario);
    }
    public void eliminarPorIdUsuario(Long id) {
        try {
            usuarioGateway.elimnarPorID(id);
        } catch (Exception e) {
            throw  new RuntimeException("No se elimino el usuario: ");
        }
    }
    public Usuario buscarPorId(Long id) {
        try {
            return usuarioGateway.buscarPorID(id);
        }catch (Exception e) {
            System.out.println(e.getMessage());

            Usuario usuarioVacio = new Usuario();
            return usuarioVacio;
        }
    }
    public Usuario buscarPorEmail(String email) {
        return usuarioGateway.buscarPorEmail(email);
    }


}

