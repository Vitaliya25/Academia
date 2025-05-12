package com.academia.academia.service;

import com.academia.academia.entity.Alumno;
import com.academia.academia.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImp implements AlumnoService {
    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImp(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    // Obtener todos los alumnos
    @Override
    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    // Obtener un alumno por ID
    @Override
    public Optional<Alumno> obtenerPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    // Crear o actualizar un alumno
    @Override
    public Alumno guardar(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    // Eliminar un alumno
    @Override
    public void eliminar(Long id) {
        alumnoRepository.deleteById(id);
    }
}