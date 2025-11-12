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
        try{
            //ISBLANK= verifica si un valor esta nulo o con espacios en blanco
            if(usuario.getNombre()==null||usuario.getNombre().isBlank()||
                    usuario.getEmail()==null|| usuario.getEmail().isBlank()||
                    usuario.getPassword()==null ||usuario.getPassword().isBlank()||
                    usuario.getUbicacion()==null||usuario.getUbicacion().isBlank()||
                    usuario.getTipoUsuario()==null||usuario.getTipoUsuario().isBlank()||
                    usuario.getEdad()==null){
                throw new IllegalArgumentException("Todos loss datos deben ser completados");
            }
            if(!usuario.getEmail().contains("@")){
                throw new IllegalArgumentException("El correo debe conter '@'");
            }
            if(usuario.getEdad()<15){
                throw new IllegalArgumentException("El edad debe ser mayor a 15");
            }
            if(!esPasswordSegura(usuario.getPassword())){
                throw new IllegalArgumentException("La contraseña debe contener 8 caracteres, 1 mayuscula y 1 simbolo");
            }
            String passwordEncriptado= encrypterGateway.encrypt(usuario.getPassword());
            usuario.setPassword(passwordEncriptado);
            return usuarioGateway.guardar(usuario);

        } catch (IllegalArgumentException e) {
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al guardar el usuario"+e.getMessage());
        }

    }
    private boolean esPasswordSegura(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&        // al menos una mayúscula
                password.matches(".*[a-z].*") &&        // al menos una minúscula
                password.matches(".*\\d.*") &&          // al menos un número
                password.matches(".*[!@#$%^&*()_+=<>?/{},\\[\\]\\-].*");  // al menos un símbolo
    }
    public Usuario loginUsuario (String email, String password) {
        try {
            Usuario usuario = buscarPorEmail(email);
            if (usuario == null || usuario.getEmail() == null) {
                throw new IllegalArgumentException("Usuario no encontrado");
            }
            if (!encrypterGateway.checkPass(password, usuario.getPassword())) {
                throw new IllegalArgumentException("Contraseña incorrecta");
            }
            return usuario;
        }catch (Exception e){
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    public Usuario actualizarUsuario (Usuario usuario) {
        if (usuario.getId()==null){
            throw new IllegalArgumentException("Es necesario el ID para actualizar");
        }

        Usuario usuarioExiste= buscarPorId(usuario.getId());
        if(usuarioExiste == null){
            throw new IllegalArgumentException("Usuario con ID"+usuario.getId()+"no encontrado");
        }
        //TRIM= elimina espacios en blanco
        if (usuario.getPassword()==null||usuario.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        usuario.setPassword(encrypterGateway.encrypt(usuario.getPassword()));
        return usuarioGateway.guardar(usuario);
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
        try {
            return usuarioGateway.buscarPorEmail(email);
        }catch(Exception e){
            throw new IllegalArgumentException("No existe usuario con el email " + email);
        }
    }

}

