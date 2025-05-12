package com.example.academia.repository

import com.example.academia.model.Alumno
import com.example.academia.model.Pago
import com.example.academia.network.PagoApiService
import com.example.academia.network.RetrofitInstance
import retrofit2.Response

class PagoRepository{

    suspend fun obtenerTodos(): List<Pago> {
        return RetrofitInstance.pagoApi.getTodosLosPagos()
    }
    suspend fun buscarPagoPorId(id: Long): Pago {
        return RetrofitInstance.pagoApi.getPagoPorId(id)
    }
    suspend fun obtenerPorAlumno(alumnoId: Long): List<Pago> {
        return RetrofitInstance.pagoApi.getPagosPorAlumno(alumnoId)
    }
    suspend fun obtenerPorFechaMensualidad(fecha: String): List<Pago> {
        return RetrofitInstance.pagoApi.getPagosPorFechaMensualidad(fecha)
    }
    suspend fun crearPago(pago: Pago): Response<Pago> {
        return RetrofitInstance.pagoApi.crearPago(pago)
    }
    suspend fun actualizarPago(id: Long, pago: Pago): Pago {
        return RetrofitInstance.pagoApi.actualizarPago(id, pago)
    }
    suspend fun eliminarPago(id: Long): Response<Unit> {
        return RetrofitInstance.pagoApi.eliminarPago(id)
    }
}
