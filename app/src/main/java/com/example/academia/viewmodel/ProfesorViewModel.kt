package com.example.academia.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.model.Profesor
import com.example.academia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class ProfesorViewModel : ViewModel() {

        private val _profesores = MutableStateFlow<List<Profesor>>(emptyList())
        val profesores: StateFlow<List<Profesor>> = _profesores.asStateFlow()

        private val _profesor = MutableStateFlow<Profesor?>(null)
        val profesor: StateFlow<Profesor?> = _profesor

        private val apiService = RetrofitInstance.profesorApi
        private val _error = mutableStateOf<String?>(null)

        val error: State<String?> get() = _error

        fun obtenerProfesores() {
            viewModelScope.launch {
                try {
                    _profesores.value = apiService.getProfesores()
                    _error.value = null
                } catch (e: Exception) {
                    _error.value = "Error al obtener los profesores: ${e.message}"
                }
            }
        }

        fun buscarProfesoresPorNombreApellido(param: String) {
            viewModelScope.launch {
                try {
                    val response = apiService.buscarProfesoresPorNombreOApellido(param)
                    if (response.isSuccessful) {
                        _profesores.value = response.body() ?: emptyList()
                        _error.value = null
                    } else {
                        _error.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _error.value = "Error al obtener los profesores: ${e.message}"
                }
            }
        }

        fun obtenerProfesorPorId(id: Long) {
            viewModelScope.launch {
                try {
                    _profesor.value = apiService.buscarProfesorPorId(id)
                } catch (e: Exception) {
                    _profesor.value = null
                }
            }
        }

        fun actualizarProfesor(profesor: Profesor) {
            viewModelScope.launch {
                try {
                    apiService.actualizarProfesor(profesor.id, profesor)
                    _profesor.value = profesor
                } catch (e: Exception) {
                    _profesor.value = null
                }
            }
        }

        fun eliminarProfesor(id: Long?) {
            viewModelScope.launch {
                try {
                    apiService.eliminarProfesor(id)
                    obtenerProfesores()
                } catch (e: Exception) {
                    Log.e("ProfesorViewModel", "Error al eliminar profesor", e)
                }
            }
        }

        fun crearProfesor(profesor: Profesor) {
            viewModelScope.launch {
                try {
                    apiService.crearProfesor(profesor)
                    _profesor.value = profesor
                    obtenerProfesores()
                } catch (e: Exception) {
                    _profesor.value = null
                    Log.e("ProfesorViewModel", "Error creando profesor", e)
                }
            }
        }

    fun asignarClaseAProfesor(profesorId: Long, claseId: Long) {
        viewModelScope.launch {
            try {
                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = claseId.toString().toRequestBody(mediaType)

                val updatedProfesor = RetrofitInstance.profesorApi.asignarClase(profesorId, requestBody)

                _profesor.value = updatedProfesor
                obtenerProfesorPorId(profesorId)

            } catch (e: Exception) {
                Log.e("ProfesorViewModel", "Error al asignar clase", e)
                _profesor.value = null
            }
        }
    }

}