package com.example.academia.repository

import com.example.academia.model.Alumno
import com.example.academia.model.Clase
import com.example.academia.network.RetrofitInstance

class ClaseRepository {

    suspend fun obtenerClases(): List<Clase> {
        return RetrofitInstance.claseApi.getClases()
    }
    suspend fun buscarClasePorId(id: Long): Clase{
        return RetrofitInstance.claseApi.buscarClasePorId(id)
    }

}
