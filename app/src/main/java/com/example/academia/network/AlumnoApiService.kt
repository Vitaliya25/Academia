package com.example.academia.network

import com.example.academia.model.Alumno
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AlumnoApiService {
    @GET("api/alumnos")
    suspend fun getAlumnos(): List<Alumno>

    @GET("api/alumnos/nombre")
    suspend fun buscarAlumnosPorNombre(
        @Query("nombre") nombre: String?,
    ): List<Alumno>

    @GET("api/alumnos/buscar")
    suspend fun buscarAlumnosPorNombreOApellido(
        @Query("param") param: String
    ): Response<List<Alumno>> // mejor usar Response para manejar errores como 204


    @GET("api/alumnos/curso")
    suspend fun buscarAlumnosPorCurso(
        @Query("curso") curso: String?
    ): List<Alumno>

    @GET("api/alumnos/{id}")
    suspend fun buscarAlumnosPorId(
        @Path("id") id: Long
    ): Alumno

    @PUT("api/alumnos/{id}")
    suspend fun actualizarAlumno(
        @Path("id") id: Long?,
        @Body alumno: Alumno
    ): Response<Unit>

    @DELETE("api/alumnos/{id}")
    suspend fun eliminarAlumno(@Path("id") id: Long?): Response<Unit>


    @POST("api/alumnos")
    suspend fun crearAlumno(@Body alumno: Alumno): Response<Alumno>


}
