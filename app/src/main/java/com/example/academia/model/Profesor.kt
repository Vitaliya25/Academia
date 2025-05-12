package com.example.academia.model

data class Profesor(
    val id: Long? = null,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val email: String,
    val clases: List<Clase> = emptyList()

)
