package com.agroguide.auth.infraestructure.entry_points;

import com.agroguide.auth.domain.model.Usuario;
import com.agroguide.auth.domain.usecase.UsuarioUseCase;
import com.agroguide.auth.infraestructure.driver_adapters.UsuarioData;
import com.agroguide.auth.infraestructure.entry_points.dto.UsuarioRequest;
import com.agroguide.auth.infraestructure.mapper.MapperUsuario;
import com.agroguide.auth.infraestructure.mapper.UsuarioRequestMapper;
import lombok.RequiredArgsConstructor;;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
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
                return new ResponseEntity<>("Error al registrar usuario", HttpStatus.CONFLICT);
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado"+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try{
            String mensajeRespuesta=usuarioUseCase.loginUsuario(
                    usuarioRequest.getEmail(),
                    usuarioRequest.getPassword()
            );
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Fallo en el logueo",HttpStatus.OK);
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
    @PutMapping("/update")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try{
            Usuario usuario = usuarioRequestMapper.toUsuario(usuarioRequest );
            Usuario usuarioValidadoActualizado= usuarioUseCase.actualizarUsuario(usuario);
            return  new ResponseEntity<>(usuarioValidadoActualizado, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
