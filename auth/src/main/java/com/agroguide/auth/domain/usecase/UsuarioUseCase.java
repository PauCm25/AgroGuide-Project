package com.agroguide.auth.domain.usecase;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.domain.model.gateway.EcrypterGateway;
import com.agroguide.auth.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j //inyecta log por medio de Loombok
public class UsuarioUseCase {
    private final UsuarioGateway usuarioGateway;
    private  final EcrypterGateway ecrypterGateway;

    public Usuario guardarUsuario (Usuario usuario) {
        //si da error, en los logs aparece ese mensaje
        if (usuario.getEmail() == null && usuario.getPassword() == null) {
            //Arrojar excepciones
            throw new NullPointerException("Datos Invalidos - guardarUsuario");
        }
        String passwordEncrypt= encrypterGateway.encrypt (usuario.getPassword());
        usuario.setPassword(passwordEncrypt);
        return usuarioGateway.guardar(usuario);
    }
    public String loginUsuario (String email, String password) {
        Usuario usuario = usuarioGateway.buscarPorEmail(email);
        if (usuario.getEmail()==null || usuario.getPassword()==null) {
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
        usuario.setPassword(ecrypterGateway.encrypt(usuario.getPassword()));
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

            Usuario usuarioVacio=new Usuario(usuarioData.getId(), usuarioData.getNombre(), usuarioData.getEmail(), usuarioData.getTipoUsuario(), usuarioData.getUbicación(), usuarioData.getEdad());
            return usuarioVacio;
        }
    }
    public Usuario buscarPorEmail(String email) {
        return usuarioGateway.buscarPorEmail(email);
    }


}

