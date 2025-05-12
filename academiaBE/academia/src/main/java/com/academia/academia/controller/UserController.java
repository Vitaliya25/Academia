package com.academia.academia.controller;

import com.academia.academia.entity.Usuario;
import com.academia.academia.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = userService.obtenerTodos();
        if (usuarios.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = userService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo alumno
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() != null)
            return ResponseEntity.badRequest().build();
        this.userService.guardar(usuario);
        return ResponseEntity.ok(usuario);
    }

    // Actualizar un alumno existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (userService.obtenerPorId(id).isPresent()) {
            usuario.setId(id);
            Usuario usuarioActualizado = userService.guardar(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (userService.obtenerPorId(id).isPresent()) {
            userService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        //String email = loginData.get("email");
        String username = loginData.get("username");
        String password = loginData.get("password");

        //Optional<Usuario> usuario = userService.obtenerPorEmail(email);
        Optional<Usuario> usuario = userService.obtenerPorUsername(username);

        if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña inválidos");
        }
    }

}