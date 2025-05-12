package com.example.academia.network

import com.example.academia.model.Profesor
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfesorApiService {

        @GET("api/profesores")
        suspend fun getProfesores(): List<Profesor>

        @GET("api/profesores/buscar")
        suspend fun buscarProfesoresPorNombreOApellido(
            @Query("param") param: String
        ): Response<List<Profesor>> // Se usa Response para manejar errores o respuestas vacías

        @GET("api/profesores/{id}")
        suspend fun buscarProfesorPorId(
            @Path("id") id: Long
        ): Profesor

        @PUT("api/profesores/{id}")
        suspend fun actualizarProfesor(
            @Path("id") id: Long?,
            @Body profesor: Profesor
        ): Response<Unit>

        @DELETE("api/profesores/{id}")
        suspend fun eliminarProfesor(
            @Path("id") id: Long?
        ): Response<Unit>

        @POST("api/profesores")
        suspend fun crearProfesor(
            @Body profesor: Profesor
        ): Response<Profesor>

    @PUT("api/profesores/{id}/clase")
    suspend fun asignarClase(
        @Path("id") profesorId: Long,
        @Body claseId: RequestBody
    ): Profesor



    }