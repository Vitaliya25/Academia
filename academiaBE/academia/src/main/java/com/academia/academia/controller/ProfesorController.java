package com.academia.academia.controller;

import com.academia.academia.entity.Alumno;
import com.academia.academia.entity.Clase;
import com.academia.academia.entity.Profesor;
import com.academia.academia.service.ClaseService;
import com.academia.academia.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    private ProfesorService profesorService;
    private ClaseService claseService;

    public ProfesorController(ProfesorService profesorService, ClaseService claseService) {

        this.profesorService = profesorService;
        this.claseService = claseService;
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
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        Optional<Profesor> profesorOpt = profesorService.obtenerPorId(id);
        if (profesorOpt.isPresent()) {
            Profesor profesorExistente = profesorOpt.get();

            if (profesor.getClases() == null) {
                profesor.setClases(profesorExistente.getClases());
            }

            profesor.setId(id);

            // Asegurarse de que las clases no se pierdan
            if (profesor.getClases() != null) {
                for (Clase clase : profesor.getClases()) {
                    clase.setProfesor(profesor);
                }
            }

            Profesor profesorActualizado = profesorService.guardarProfesor(profesor);
            System.out.println("Recibido en update: " + profesor.getClases());

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


    @GetMapping("/buscar")
    public ResponseEntity<List<Profesor>> buscarPorNombreOApellido(@RequestParam String param) {
        if (param == null || param.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Profesor> resultados = profesorService.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(param, param);

        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultados);
    }

    @PutMapping("/{id}/clases")
    public ResponseEntity<Profesor> asignarClasesAProfesor(@PathVariable Long id, @RequestBody List<Long> idsClases) {
        Optional<Profesor> profesorOpt = profesorService.obtenerPorId(id);
        if (profesorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profesor profesor = profesorOpt.get();
        List<Clase> clases = claseService.getAllClases();
        profesor.setClases(clases);
        Profesor actualizado = profesorService.guardarProfesor(profesor);

        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/{id}/clase")
    public ResponseEntity<Profesor> asignarClaseAProfesor(@PathVariable Long id, @RequestBody Long idClase) {
        Optional<Profesor> profesorOpt = profesorService.obtenerPorId(id);
        if (profesorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profesor profesor = profesorOpt.get();

        Optional<Clase> claseOpt = claseService.getClaseById(idClase);
        if (claseOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // o un 400 Bad Request si prefieres
        }
        Clase clase = claseOpt.get();

        // Verificamos si la clase ya está asignada al profesor
        if (!profesor.getClases().contains(clase)) {
            if (profesor.getClases() == null) {
                profesor.setClases(new ArrayList<>());
            }

            // Asignamos la clase al profesor y viceversa
            clase.setProfesor(profesor);  // Establecemos la relación bidireccional
            profesor.getClases().add(clase);  // Añadimos la clase a la lista del profesor

            // Guardamos tanto el profesor como la clase
            claseService.saveClase(clase);
            profesorService.guardarProfesor(profesor);
        }

        return ResponseEntity.ok(profesor); // Devolvemos el profesor actualizado
    }

}
