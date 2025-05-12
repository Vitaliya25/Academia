package com.academia.academia.controller;

import com.academia.academia.entity.Alumno;
import com.academia.academia.entity.Pago;
import com.academia.academia.service.PagoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // Ver todos los pagos
    @GetMapping
    public List<Pago> obtenerTodosLosPagos() {
        return pagoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPago(@PathVariable Long id) {
        Optional<Pago> pago = pagoService.obtenerPorId(id);
        return pago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ver pagos por alumno
    @GetMapping("/alumno/{alumnoId}")
    public List<Pago> obtenerPagosPorAlumno(@PathVariable Long alumnoId) {
        return pagoService.obtenerPorAlumno(alumnoId);
    }

    // Obtener pagos por fecha exacta de mensualidad
    @GetMapping("/mensualidad")
    public List<Pago> obtenerPagosPorFechaMensualidad(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaMensualidad) {
        return pagoService.obtenerPorFechaMensualidad(fechaMensualidad);
    }

    // Registrar un nuevo pago
    @PostMapping
    public ResponseEntity<Pago> registrarPago(@RequestBody Pago pago) {
        if (pago.getId() != null)
            return ResponseEntity.badRequest().build();
        this.pagoService.guardar(pago);
        return ResponseEntity.ok(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        if (pagoService.obtenerPorId(id).isPresent()) {
            pago.setId(id);
            Pago pagoActualizado = pagoService.guardar(pago);
            return ResponseEntity.ok(pagoActualizado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

}
