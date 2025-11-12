package com.agroguide.auth.infraestructure.entry_points;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.domain.usecase.UsuarioUseCase;
import com.agroguide.auth.infraestructure.entry_points.dto.LoginResponse;
import com.agroguide.auth.infraestructure.entry_points.dto.UsuarioRequest;
import com.agroguide.auth.infraestructure.mapper.UsuarioRequestMapper;
import lombok.RequiredArgsConstructor;;
import lombok.extern.java.Log;
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
    private final UsuarioRequestMapper usuarioRequestMapper;
    @PostMapping("/Registro")
        public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        List<String>errores =new ArrayList<>();
        //PREGUNTAS: Se hiceron estas validaciones debido a que al momento de que existen al
        // mismo tiempo variables nullas o con errores, se envia un solo mensaje y no todos al tiempo
        if (usuarioRequest.getNombre()==null||usuarioRequest.getNombre().isBlank()){
            errores.add("El nombre es requerido");
        }
        if (usuarioRequest.getEmail()==null ||usuarioRequest.getEmail().isBlank()){
            errores.add("El email es oblogatorio");
        }else if(!usuarioRequest.getEmail().contains(("@"))){
            errores.add("El email debe tener '@'");
        }
        if (usuarioRequest.getPassword()==null ||usuarioRequest.getPassword().isBlank()){
            errores.add("Debe seleccionar una región");
        }
        if(usuarioRequest.getEdad()==null|| usuarioRequest.getEdad()<15){
            errores.add("El edad debe ser mayor a 15");
        }
        if (!errores.isEmpty()) {
            return new ResponseEntity<>(String.join("|", errores), HttpStatus.OK);
        }
        try{
            Usuario usuario=usuarioRequestMapper.toUsuario(usuarioRequest);
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
    public ResponseEntity<LoginResponse> login(@RequestBody UsuarioRequest usuarioRequest){
        try { Usuario usuario = usuarioRequestMapper.toUsuario(usuarioRequest);
            Usuario usuarioValidadoLogin = usuarioUseCase.loginUsuario(usuario.getEmail(), usuario.getPassword());
            if (usuarioValidadoLogin!=null && usuarioValidadoLogin.getId()!=null) {
                LoginResponse respuesta = new LoginResponse("Bienvenido",
                        //SON LAS VARIBALES QUE RETORNA EN EL USUARIO
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
            return ResponseEntity.status(HttpStatus.OK).body(error);
        }
    }
    //Buscar
//    @GetMapping("/{id}")
//    public ResponseEntity<Usuario> findByIdUsuario(@PathVariable Long id){
//
//        Usuario usuarioValidadoEncontrado = usuarioUseCase.buscarPorId(id);
//        if (usuarioValidadoEncontrado.getId() != null){
//            return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.CONFLICT);
//    }
//ENCONTRAR USUARIO
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRequest> findByIdUsuario(@PathVariable Long id){
        try{
            Usuario usuarioValidadoEncontrado= usuarioUseCase.buscarPorId(id);
            if(usuarioValidadoEncontrado.getId()!=null){
                UsuarioRequest usuarioRequest= new UsuarioRequest();
                usuarioRequest.setId(usuarioValidadoEncontrado.getId());
                usuarioRequest.setNombre(usuarioValidadoEncontrado.getNombre());
                usuarioRequest.setEmail(usuarioValidadoEncontrado.getEmail());
                usuarioRequest.setPassword(usuarioValidadoEncontrado.getPassword());// no se debe ver
                usuarioRequest.setTipoUsuario(usuarioValidadoEncontrado.getTipoUsuario());
                usuarioRequest.setUbicacion(usuarioValidadoEncontrado.getUbicacion());
                usuarioRequest.setEdad(usuarioValidadoEncontrado.getEdad());
                return new ResponseEntity<>(usuarioRequest, HttpStatus.OK);
            }
            UsuarioRequest usuarioVacio=new UsuarioRequest();
            return new ResponseEntity<>(usuarioVacio, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<LoginResponse> updateUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            Usuario usuario = usuarioRequestMapper.toUsuario(usuarioRequest);
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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
