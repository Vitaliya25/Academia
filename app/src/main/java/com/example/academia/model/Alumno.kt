package com.example.academia.model

data class Alumno(
    val id: Long? = null,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val curso: String,
    val telefono: String,
    val email: String
)