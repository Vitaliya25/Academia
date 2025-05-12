package com.example.academia.model

data class Clase(
    val id: Long? = null,
    val asignatura: String,
    val curso: String,
    val profesor: Profesor?,
    val profesorId: Long?,//=null
    val alumnos: List<Alumno> = emptyList(),
    val horarios: List<Horario> = emptyList()

)
