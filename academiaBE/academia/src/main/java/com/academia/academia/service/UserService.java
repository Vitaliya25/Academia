package com.academia.academia.service;

import com.academia.academia.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Usuario> obtenerTodos();
    Optional<Usuario> obtenerPorId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);

    Optional<Usuario> obtenerPorUsername(String username);
    Optional<Usuario> obtenerPorEmail(String email);
}
