package com.example.academia.model

data class MenuItem(
    val title: String,  // Título del ítem
    val description: String,  // Descripción del ítem
    val colorResId: Int,  // ID del recurso de color
    val onClick: () -> Unit  // Acción para ejecutar al hacer clic
)
