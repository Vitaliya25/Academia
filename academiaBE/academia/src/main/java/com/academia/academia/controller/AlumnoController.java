package com.academia.academia.controller;

import com.academia.academia.entity.Alumno;
import com.academia.academia.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // Obtener todos los alumnos
    @GetMapping
    public ResponseEntity<List<Alumno>> obtenerAlumnos() {
        List<Alumno> alumnos = alumnoService.obtenerTodos();
        if (alumnos.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(alumnos);
    }

    // Obtener un alumno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerAlumno(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoService.obtenerPorId(id);
        return alumno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo alumno
    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@RequestBody Alumno alumno) {
     if (alumno.getId() != null)
            return ResponseEntity.badRequest().build();
        this.alumnoService.guardar(alumno);
        return ResponseEntity.ok(alumno);
    }

    // Actualizar un alumno existente
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        if (alumnoService.obtenerPorId(id).isPresent()) {
            alumno.setId(id);
            Alumno alumnoActualizado = alumnoService.guardar(alumno);
            return ResponseEntity.ok(alumnoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        if (alumnoService.obtenerPorId(id).isPresent()) {
            alumnoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<Alumno>> buscarAlumnosPorNombre(@RequestParam String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Alumno> alumnos = alumnoService.findByNombreContainingIgnoreCase(nombre);

        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(alumnos);
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<Alumno>> buscarAlumnosPorNombreOApellido(@RequestParam String param) {
        if (param == null || param.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Alumno> resultados = alumnoService.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(param, param);

        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultados);
    }


    @GetMapping("/curso")
    public ResponseEntity<List<Alumno>> buscarAlumnosPorCurso(@RequestParam String curso) {
        if (curso == null || curso.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Alumno> alumnos = alumnoService.findByCursoContainingIgnoreCase(curso);

        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(alumnos);
    }


}