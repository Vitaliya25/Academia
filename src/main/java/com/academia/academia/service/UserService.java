package com.academia.academia.service;

import com.academia.academia.entity.Alumno;
import com.academia.academia.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> obtenerTodos();
    Optional<User> obtenerPorId(Long id);
    User guardar(User user);
    void eliminar(Long id);
}
