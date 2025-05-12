package com.academia.academia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Alumno
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Alumno alumno;

    private LocalDate fechaMensualidad;

    private double cantidad;     // Monto pagado

    private LocalDate fechaPago; // Cuándo se realizó el pago



    public Pago() {}

    public Pago(LocalDate fechaMensualidad, double cantidad, LocalDate fechaPago) {
        this.fechaMensualidad = fechaMensualidad;
        this.cantidad = cantidad;
        this.fechaPago = fechaPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public LocalDate getFechaMensualidad() {
        return fechaMensualidad;
    }

    public void setFechaMensualidad(LocalDate fechaMensualidad) {
        this.fechaMensualidad = fechaMensualidad;
    }
}

