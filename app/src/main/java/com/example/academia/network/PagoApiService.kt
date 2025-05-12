package com.example.academia.network
import com.example.academia.model.Pago
import retrofit2.Response
import retrofit2.http.*

interface PagoApiService {

    @GET("api/pagos")
    suspend fun getTodosLosPagos(): List<Pago>

    @GET("api/pagos/{id}")
    suspend fun getPagoPorId(@Path("id") id: Long): Pago

    @GET("api/pagos/alumno/{alumnoId}")
    suspend fun getPagosPorAlumno(@Path("alumnoId") alumnoId: Long): List<Pago>

    @GET("api/pagos/mensualidad")
    suspend fun getPagosPorFechaMensualidad(@Query("fecha") fecha: String): List<Pago>

    @POST("api/pagos")
    suspend fun crearPago(@Body pago: Pago): Response<Pago>

    @PUT("api/pagos/{id}")
    suspend fun actualizarPago(@Path("id") id: Long, @Body pago: Pago): Pago

    @DELETE("api/pagos/{id}")
    suspend fun eliminarPago(@Path("id") id: Long): Response<Unit>
}
