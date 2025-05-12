package com.academia.academia.service;

import com.academia.academia.entity.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {
    List<Alumno> obtenerTodos();
    Optional<Alumno> obtenerPorId(Long id);
    Alumno guardar(Alumno alumno);
    void eliminar(Long id);
}
