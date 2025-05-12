package com.example.academia.repository

import com.example.academia.model.Alumno
import com.example.academia.network.RetrofitInstance
import retrofit2.Response

class AlumnoRepository {
    suspend fun obtenerAlumnos(): List<Alumno> {
        return RetrofitInstance.alumnoApi.getAlumnos()
    }

    suspend fun buscarAlumnosPorNombre(nombre: String?): List<Alumno> {
        return RetrofitInstance.alumnoApi.buscarAlumnosPorNombre(nombre)
    }

    suspend fun buscarAlumnosPorNombreApellido(nombre: String?): Response<List<Alumno>> {
        return if (!nombre.isNullOrBlank()) {
            RetrofitInstance.alumnoApi.buscarAlumnosPorNombreOApellido(nombre)
        } else {
            Response.success(emptyList()) // o lanza una excepción, según lo que necesites
        }
    }

    suspend fun buscarAlumnosPorCurso(curso: String?): List<Alumno> {
        return RetrofitInstance.alumnoApi.buscarAlumnosPorCurso(curso)
    }

    suspend fun buscarAlumnosPorId(id: Long): Alumno {
        return RetrofitInstance.alumnoApi.buscarAlumnosPorId(id)
    }
}
