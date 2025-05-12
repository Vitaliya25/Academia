package com.example.academia.model

data class Pago(
    val id: Long? = null,
    val fechaMensualidad: String,
    val cantidad: Long,
    val fechaPago: String = "",
    val alumno: Alumno?,
    val alumnoId: Long?//=null

)
