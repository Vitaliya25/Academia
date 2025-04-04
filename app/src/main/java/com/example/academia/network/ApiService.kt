package com.example.academia.network
import com.example.academia.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/usuarios")
    fun obtenerUsuarios(): Call<List<User>>

    @POST("api/usuarios")
    fun crearUsuario(@Body user: User): Call<User>

    @GET("api/usuarios/{id}")
    fun obtenerUsuarioPorId(@Path("id") id: Long): Call<User>
}