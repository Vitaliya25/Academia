package com.example.academia.repository

import com.example.academia.model.Horario
import com.example.academia.network.HorarioApiService
import retrofit2.Response

class HorarioRepository(private val horarioApiService: HorarioApiService) {

    // Función para obtener todos los horarios
    suspend fun getHorarios(): List<Horario> {
        return horarioApiService.getHorarios()
    }

    // Función para obtener un horario por ID
    suspend fun buscarHorarioPorId(id: Long): Horario {
        return horarioApiService.buscarHorarioPorId(id)
    }

    // Función para crear un nuevo horario
    suspend fun crearHorario(horario: Horario): Horario {
        return horarioApiService.crearHorario(horario)
    }

    // Función para actualizar un horario
    suspend fun actualizarHorario(id: Long, horario: Horario): Horario {
        return horarioApiService.actualizarHorario(id, horario)
    }

    // Función para eliminar un horario
    suspend fun eliminarHorario(id: Long): Response<Unit> {
        return horarioApiService.eliminarHorario(id)
    }
}
