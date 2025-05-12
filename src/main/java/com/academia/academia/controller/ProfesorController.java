package com.academia.academia.controller;

import com.academia.academia.entity.Alumno;
import com.academia.academia.entity.Profesor;
import com.academia.academia.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    private ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerProfesores() {
        List<Profesor> profesores = profesorService.obtenerTodos();
        if (profesores.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(profesores);    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerProfesor(@PathVariable Long id) {
        Optional<Profesor> profesor = profesorService.obtenerPorId(id);
        return profesor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@RequestBody Profesor profesor) {
        if (profesor.getId() != null)
            return ResponseEntity.badRequest().build();
        this.profesorService.guardarProfesor(profesor);
        return ResponseEntity.ok(profesor);
    }

    // Actualizar un profesor existente
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarAlumno(@PathVariable Long id, @RequestBody Profesor profesor) {
        if (profesorService.obtenerPorId(id).isPresent()) {
            profesor.setId(id);
            Profesor profesorActualizado = profesorService.guardarProfesor(profesor);
            return ResponseEntity.ok(profesorActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un profesor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        if (profesorService.obtenerPorId(id).isPresent()) {
            profesorService.eliminarProfesor(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
