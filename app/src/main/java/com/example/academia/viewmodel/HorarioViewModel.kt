package com.example.academia.viewmodel


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.model.Alumno
import com.example.academia.model.Horario
import com.example.academia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HorarioViewModel : ViewModel() {

    private val _horarios = MutableStateFlow<List<Horario>>(emptyList())
    val horarios: StateFlow<List<Horario>> = _horarios.asStateFlow()

    private val _horario = MutableStateFlow<Horario?>(null)
    val horario: StateFlow<Horario?> = _horario

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    private val apiService = RetrofitInstance.horarioApi // Asumimos que tenés esto configurado

    fun obtenerHorarios() {
        viewModelScope.launch {
            try {
                _horarios.value = apiService.getHorarios()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener los horarios: ${e.message}"
            }
        }
    }

    fun obtenerHorarioPorId(id: Long) {
        viewModelScope.launch {
            try {
                _horario.value = apiService.buscarHorarioPorId(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener el horario: ${e.message}"
                _horario.value = null
            }
        }
    }

    fun crearHorario(horario: Horario) {
        viewModelScope.launch {
            try {
                val creado = apiService.crearHorario(horario)
                _horario.value = creado
                obtenerHorarios() // Actualiza la lista luego de crear
            } catch (e: Exception) {
                _error.value = "Error al crear el horario: ${e.message}"
                _horario.value = null
            }
        }
    }

    fun actualizarHorario(horario: Horario) {
        viewModelScope.launch {
            try {
                apiService.actualizarHorario(horario.id!!, horario)
                _horario.value = horario
                obtenerHorarios()
            } catch (e: Exception) {
                _error.value = "Error al actualizar el horario: ${e.message}"
                _horario.value = null
            }
        }
    }

    fun eliminarHorario(id: Long?) {
        viewModelScope.launch {
            try {
                if (id != null) {
                    apiService.eliminarHorario(id)
                    obtenerHorarios()
                }
            } catch (e: Exception) {
                Log.e("HorarioViewModel", "Error al eliminar el horario", e)
            }
        }
    }
}
