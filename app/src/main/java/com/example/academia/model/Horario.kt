package com.example.academia.model

import java.time.LocalTime

data class Horario(
    val id: Long? = null,
    val dia: DiaSemana,
    val horaInicio: String,
    val horaFin: String
)