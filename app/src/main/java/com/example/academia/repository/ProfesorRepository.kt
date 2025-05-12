package com.example.academia.repository

import com.example.academia.model.Profesor
import com.example.academia.network.RetrofitInstance

class ProfesorRepository {

    suspend fun obtenerProfesores(): List<Profesor> {
        return RetrofitInstance.profesorApi.getProfesores()
    }

    suspend fun buscarProfesoresPorNombreApellido(param: String): List<Profesor> {
        val response = RetrofitInstance.profesorApi.buscarProfesoresPorNombreOApellido(param)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            return emptyList() // Manejo básico si hay error o no hay datos
        }
    }

    suspend fun buscarProfesorPorId(id: Long): Profesor {
        return RetrofitInstance.profesorApi.buscarProfesorPorId(id)
    }
}