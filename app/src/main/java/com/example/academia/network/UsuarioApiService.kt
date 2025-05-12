package com.example.academia.network
import com.example.academia.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface UsuarioApiService {

    @GET("api/usuarios")
    suspend fun obtenerUsuarios(): List<Usuario>

    @GET("api/usuarios/{id}")
    suspend fun obtenerUsuarioPorId(@Path("id") id: Long): Usuario

    @POST("api/usuarios")
    suspend fun crearUsuario(@Body usuario: Usuario): Usuario

    @PUT("api/usuarios/{id}")
    suspend fun actualizarUsuario(@Path("id") id: Long, @Body usuario: Usuario): Usuario

    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long)

    @POST("api/usuarios/login")
    suspend fun login(@Body loginData: Map<String, String>): Response<Void>


}
