package com.agroguide.auth.infraestructure.entry_points;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.domain.usecase.UsuarioUseCase;
import com.agroguide.auth.infraestructure.driver_adapters.UsuarioData;
import com.agroguide.auth.infraestructure.entry_points.dto.LoginResponse;
import com.agroguide.auth.infraestructure.mapper.MapperUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/api/agroguide/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioUseCase usuarioUseCase;
    private final MapperUsuario mapperUsuario;
    @PostMapping("/Registro")
        public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioData usuarioData ) {

        try{
            Usuario usuario=mapperUsuario.toUsuario(usuarioData);
            Usuario usuarioValidadoGuardado=usuarioUseCase.guardarUsuario(usuario);
            if(usuarioValidadoGuardado.getId()!=null){
                return new ResponseEntity<>("Usuario registrado correctamente",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Error al registrar usuario", HttpStatus.OK);
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado"+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UsuarioData usuarioData){
        try {
            Usuario usuario = mapperUsuario.toUsuario(usuarioData);
            Usuario usuarioValidadoLogin = usuarioUseCase.loginUsuario(usuario.getEmail(), usuario.getPassword());
            if (usuarioValidadoLogin!=null && usuarioValidadoLogin.getId()!=null) {
                LoginResponse respuesta = new LoginResponse("Bienvenido",
                        //SON LAS VARIABLES QUE RETORNA EN EL USUARIO
                        usuarioValidadoLogin.getId(),
                        usuarioValidadoLogin.getNombre(),
                        usuarioValidadoLogin.getTipoUsuario());
                return ResponseEntity.ok(respuesta);
            }else {
                LoginResponse respuesta = new LoginResponse
                    ( "Credenciales invalidas",
                            null, null, null);
                return ResponseEntity.ok((respuesta)); }
        } catch (IllegalArgumentException e) {
            LoginResponse error = new LoginResponse
                    ( "Error en el login: " + e.getMessage(),
                            null, null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    //Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findByIdUsuario(@PathVariable Long id){

        Usuario usuarioValidadoEncontrado = usuarioUseCase.buscarPorId(id);
        if (usuarioValidadoEncontrado.getId() != null){
            return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.OK);
        }
        return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.CONFLICT);
    }
    @PutMapping("/update")
    public ResponseEntity<LoginResponse> updateUsuario(@RequestBody UsuarioData usuarioData) {
        try {
            Usuario usuario = mapperUsuario.toUsuario(usuarioData);
            Usuario usuarioActualizado = usuarioUseCase.actualizarUsuario(usuario);
            LoginResponse respuesta = new LoginResponse(
                    "Usuario actualizado correctamente",
                    usuarioActualizado.getId(),
                    usuarioActualizado.getNombre(),
                    usuarioActualizado.getTipoUsuario()
            );
            return ResponseEntity.ok(respuesta);

        } catch (IllegalArgumentException e) {
            LoginResponse error = new LoginResponse(e.getMessage(),
                    null, null, null);
            return ResponseEntity.status(HttpStatus.OK).body(error);
        } catch (Exception e) {
            LoginResponse error = new LoginResponse
                    ("Error al actualizar el usuario: ",
                            null, null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteByIdUsuario(@PathVariable Long id){
        try {
            usuarioUseCase.eliminarPorIdUsuario(id);
            return ResponseEntity.ok().body("Usario eliminado");
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
