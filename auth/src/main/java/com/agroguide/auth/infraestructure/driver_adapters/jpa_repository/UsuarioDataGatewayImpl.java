package com.agroguide.auth.infraestructure.driver_adapters.jpa_repository;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.domain.model.gateway.UsuarioGateway;
import com.agroguide.auth.infraestructure.driver_adapters.UsuarioData;
import com.agroguide.auth.infraestructure.mapper.MapperUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioDataGatewayImpl implements UsuarioGateway {
    private final MapperUsuario mapperUsuario;
    private final UsuarioDataJpaRepository repository;

    public Usuario guardar (Usuario usuario) {
        UsuarioData usuarioData= mapperUsuario.toData(usuario);
        return mapperUsuario.toUsuario(repository.save(usuarioData));
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(usuarioData -> mapperUsuario.toUsuario(usuarioData))
                .orElseThrow(() -> new RuntimeException("Fallo consulta de BD"));
    }

    @Override
    public void elimnarPorID(long id) {
        try{
            repository.deleteById(id);
        }catch (Exception error){
            throw new RuntimeException(error.getMessage());
        }
    }

    @Override
    public Usuario buscarPorID(long id) {
        return repository.findById(id)
                .map(usuarioData -> mapperUsuario.toUsuario(usuarioData))
                .orElseThrow(()->new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        UsuarioData usuarioData=mapperUsuario.toData(usuario);
        if(!repository.existsById(usuario.getId())){
            throw new RuntimeException("Usuario con id"+usuario.getId()+" no encontrado");
        }
        return mapperUsuario.toUsuario(repository.save(usuarioData));
    }
}
