package com.academia.academia.service;

import com.academia.academia.entity.Profesor;

import java.util.List;
import java.util.Optional;

public interface ProfesorService {
    List<Profesor> obtenerTodos();
    Optional<Profesor> obtenerPorId(Long id);
    Profesor guardarProfesor(Profesor profesor);
    void eliminarProfesor(Long id);

}
