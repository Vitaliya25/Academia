package com.academia.academia.service;

import com.academia.academia.entity.Pago;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface PagoService {

    List<Pago> obtenerTodos();
    Optional<Pago> obtenerPorId(Long id);
    Pago guardar(Pago pago);
    void eliminar(Long id);

    List<Pago> obtenerPorAlumno(Long alumnoId);

    List<Pago> obtenerPorFechaMensualidad(LocalDate fechaMensualidad);

    List<Pago> obtenerPorAlumnoYMensualidad(Long alumnoId, LocalDate fechaMensualidad);
}
