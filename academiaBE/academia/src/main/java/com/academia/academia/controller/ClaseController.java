package com.academia.academia.controller;

import com.academia.academia.entity.Alumno;
import com.academia.academia.entity.Clase;
import com.academia.academia.service.ClaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {

    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @GetMapping
    public List<Clase> getAllClases() {
        return claseService.getAllClases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> getClaseById(@PathVariable Long id) {
        Optional<Clase> clase = claseService.getClaseById(id);
        return clase.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Clase createClase(@RequestBody Clase clase) {
        return claseService.saveClase(clase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clase> updateClase(@PathVariable Long id, @RequestBody Clase clase) {
        Optional<Clase> existingClase = claseService.getClaseById(id);
        if (existingClase.isPresent()) {
            //clase.setId(id);
            Clase claseActualizado =existingClase.get();
            claseActualizado.setAsignatura(clase.getAsignatura());
            claseActualizado.setCurso(clase.getCurso());
            claseActualizado.setProfesor(clase.getProfesor());
            this.claseService.saveClase(claseActualizado);
            return ResponseEntity.ok(claseActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClase(@PathVariable Long id) {
        claseService.deleteClase(id);
        return ResponseEntity.noContent().build();
    }

}
