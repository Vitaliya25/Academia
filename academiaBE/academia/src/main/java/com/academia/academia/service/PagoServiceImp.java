package com.academia.academia.service;

import com.academia.academia.entity.Pago;
import com.academia.academia.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImp implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImp(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    @Override
    public List<Pago> obtenerPorAlumno(Long alumnoId) {
        return pagoRepository.findByAlumnoId(alumnoId);
    }

    @Override
    public List<Pago> obtenerPorFechaMensualidad(LocalDate fechaMensualidad) {
        return pagoRepository.findByFechaMensualidad(fechaMensualidad);
    }


    @Override
    public List<Pago> obtenerPorAlumnoYMensualidad(Long alumnoId, LocalDate fechaMensualidad) {
        return pagoRepository.findByAlumnoIdAndFechaMensualidad(alumnoId, fechaMensualidad);
    }
}
