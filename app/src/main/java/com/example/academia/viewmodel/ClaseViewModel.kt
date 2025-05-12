package com.example.academia.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.model.Clase
import com.example.academia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClaseViewModel : ViewModel() {

    private val _clases = MutableStateFlow<List<Clase>>(emptyList())
    val clases: StateFlow<List<Clase>> = _clases.asStateFlow()

    private val _clase = MutableStateFlow<Clase?>(null)
    val clase: StateFlow<Clase?> = _clase

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    private val apiService = RetrofitInstance.claseApi

    fun obtenerClases() {
        viewModelScope.launch {
            try {
                _clases.value = apiService.getClases()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener las clases: ${e.message}"
            }
        }
    }

    fun obtenerClasePorId(id: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.buscarClasePorId(id)
                _clase.value = response  // <<< AQUÍ ACTUALIZAS
                _error.value = null
            } catch (e: Exception) {
                _clase.value = null
                _error.value = "Error buscando clase: ${e.message}"
            }
        }
    }


    fun crearClase(clase: Clase) {
        Log.d("ClaseViewModel", "Creando clase: $clase")

        viewModelScope.launch {
            try {
                apiService.crearClase(clase)
                _clase.value = clase
                obtenerClases() // Actualizar lista después de crear
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error creando clase: ${e.message}"
            }
        }
    }

    fun actualizarClase(clase: Clase) {
        Log.d("ClaseViewModel", "Actualizando clase VM: $clase")
        viewModelScope.launch {
            try {
                val id = clase.id ?: throw IllegalArgumentException("El id de la clase no puede ser nulo al actualizar")
                val claseActualizada = apiService.actualizarClase(id, clase)
                _clase.value = claseActualizada
                obtenerClases()
                Log.d("ClaseViewModel", "Actualizando clase VM After: ${claseActualizada}")
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error actualizando clase: ${e.message}"
            }
        }
    }


    fun actualizarProfesorId(nuevoProfesorId: Long) {

        _clase.value = _clase.value?.copy(profesorId = nuevoProfesorId)
        Log.d("ClaseViewModel", "Actualizar ProfesorID: ${_clase.value}")

    }


    fun eliminarClase(id: Long) {
        viewModelScope.launch {
            try {
                apiService.eliminarClase(id)
                obtenerClases()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error eliminando clase: ${e.message}"
            }
        }
    }
}
