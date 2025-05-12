package com.example.academia.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL base de servidor Spring
            .addConverterFactory(GsonConverterFactory.create()) // Para convertir JSON a objetos Kotlin
            .build()
    }

    val alumnoApi: AlumnoApiService by lazy {
        retrofit.create(AlumnoApiService::class.java)
    }

    val usuarioApi: UsuarioApiService by lazy {
        retrofit.create(UsuarioApiService::class.java)
    }

    val profesorApi: ProfesorApiService by lazy {
        retrofit.create(ProfesorApiService::class.java)
    }

    val claseApi: ClaseApiService by lazy {
        retrofit.create(ClaseApiService::class.java)
    }

    val horarioApi: HorarioApiService by lazy {
        retrofit.create(HorarioApiService::class.java)
    }

    val pagoApi: PagoApiService by lazy {
        retrofit.create(PagoApiService::class.java)
    }

}