package com.academia.academia.repository;

import com.academia.academia.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByAlumnoId(Long alumnoId);

    List<Pago> findByAlumnoIdAndFechaMensualidad(Long alumnoId, LocalDate fechaMensualidad);

    List<Pago> findByFechaMensualidad(LocalDate fechaMensualidad);
}
