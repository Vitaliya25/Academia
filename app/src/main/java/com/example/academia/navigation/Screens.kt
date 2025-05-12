package com.example.academia.navigation

import kotlinx.serialization.Serializable

@Serializable
object Inicio

@Serializable
object Login

@Serializable
object Principal

@Serializable
object GestionAlumnos

@Serializable
object GestionProfesores

@Serializable
object GestionPagos

@Serializable
object GestionHorarios

@Serializable
data class AlumnoDetalle(val alumnoId: Long)

@Serializable
data class ProfesorDetalle(val profesorId: Long)

@Serializable
data class ClaseDetalle(val claseId: Long)


