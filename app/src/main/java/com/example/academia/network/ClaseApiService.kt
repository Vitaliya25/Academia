package com.example.academia.network

import com.example.academia.model.Clase
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClaseApiService {

    @GET("api/clases")
    suspend fun getClases(): List<Clase>

    @GET("api/clases/{id}")
    suspend fun buscarClasePorId(@Path("id") id: Long): Clase

    @POST("api/clases")
    suspend fun crearClase(@Body clase: Clase): Clase

    @PUT("api/clases/{id}")
    suspend fun actualizarClase(@Path("id") id: Long, @Body clase: Clase): Clase

    @DELETE("api/clases/{id}")
    suspend fun eliminarClase(@Path("id") id: Long): Response<Unit>


}
