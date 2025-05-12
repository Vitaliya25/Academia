package com.example.academia.network

import com.example.academia.model.Horario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HorarioApiService {

    @GET("api/horarios")
    suspend fun getHorarios(): List<Horario>

    @GET("api/horarios/{id}")
    suspend fun buscarHorarioPorId(@Path("id") id: Long): Horario

    @POST("api/horarios")
    suspend fun crearHorario(@Body horario: Horario): Horario

    @PUT("api/horarios/{id}")
    suspend fun actualizarHorario(@Path("id") id: Long, @Body horario: Horario): Horario

    @DELETE("api/horarios/{id}")
    suspend fun eliminarHorario(@Path("id") id: Long): Response<Unit>
}
