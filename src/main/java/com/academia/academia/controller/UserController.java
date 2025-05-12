package com.academia.academia.controller;

import com.academia.academia.entity.User;
import com.academia.academia.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<User>> obtenerUsuario() {
        List<User> usuarios = userService.obtenerTodos();
        if (usuarios.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuario(@PathVariable Long id) {
        Optional<User> usuario = userService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo alumno
    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody User usuario) {
        if (usuario.getId() != null)
            return ResponseEntity.badRequest().build();
        this.userService.guardar(usuario);
        return ResponseEntity.ok(usuario);
    }

    // Actualizar un alumno existente
    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody User usuario) {
        if (userService.obtenerPorId(id).isPresent()) {
            usuario.setId(id);
            User usuarioActualizado = userService.guardar(usuario);
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
}