package com.academia.academia.service;

import com.academia.academia.entity.Clase;

import java.util.List;
import java.util.Optional;

public interface ClaseService {
    List<Clase> getAllClases();
    Optional<Clase> getClaseById(Long id);
    Clase saveClase(Clase clase);
    void deleteClase(Long id);

    //List<Clase> getClasesByProfesorNombre(String nombre);
}