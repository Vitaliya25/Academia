package com.example.academia.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.model.Alumno
import com.example.academia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlumnoViewModel : ViewModel() {

    private val _alumnos = MutableStateFlow<List<Alumno>>(emptyList())
    val alumnos: StateFlow<List<Alumno>> = _alumnos.asStateFlow()

    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    private val apiService = RetrofitInstance.alumnoApi
    private val _error = mutableStateOf<String?>(null)

    val error: State<String?> get() = _error

    private var todosLosAlumnos: List<Alumno> = emptyList()

    fun obtenerAlumnos() {
        viewModelScope.launch {
            try {
                val alumnosApi = apiService.getAlumnos()
                todosLosAlumnos = alumnosApi
                _alumnos.value = alumnosApi
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener los alumnos: ${e.message}"
            }
        }
    }


    fun buscarAlumnosCombinado(nombre: String, curso: String) {
        val filtrados = todosLosAlumnos.filter { alumno ->
            val coincideNombre = nombre.isBlank() || alumno.nombre.contains(nombre, ignoreCase = true) || alumno.apellido.contains(nombre, ignoreCase = true)
            val coincideCurso = curso.isBlank() || alumno.curso.equals(curso, ignoreCase = true)
            coincideNombre && coincideCurso
        }
        _alumnos.value = filtrados
    }


    fun buscarAlumnosPorNombre(nombre: String) {
        viewModelScope.launch {
            try {
                _alumnos.value = apiService.buscarAlumnosPorNombre(nombre)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener los alumnos: ${e.message}"
            }
        }
    }

    fun buscarAlumnosPorNombreApellido(param: String) {
        viewModelScope.launch {
            try {
                val response = apiService.buscarAlumnosPorNombreOApellido(param)
                if (response.isSuccessful) {
                    _alumnos.value = response.body() ?: emptyList()  // Si el cuerpo es null, asigna una lista vacía
                    _error.value = null
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al obtener los alumnos: ${e.message}"
            }
        }
    }

    fun buscarAlumnosPorCurso(curso: String) {
        viewModelScope.launch {
            try {
                _alumnos.value = apiService.buscarAlumnosPorCurso(curso)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener los alumnos: ${e.message}"
            }
        }
    }

    fun obtenerAlumnoPorId(id: Long) {
        viewModelScope.launch {
            try {
                _alumno.value = apiService.buscarAlumnosPorId(id)
            } catch (e: Exception) {
                _alumno.value = null
            }
        }
    }

    fun actualizarAlumno(alumno: Alumno) {
        viewModelScope.launch {
            try {
                apiService.actualizarAlumno(alumno.id, alumno)
                _alumno.value = alumno // Actualiza el estado con el nuevo alumno
            } catch (e: Exception) {
                _alumno.value = null
            }
        }
    }

    fun eliminarAlumno(id: Long?) {
        viewModelScope.launch {
            try {
                apiService.eliminarAlumno(id)
                obtenerAlumnos() // Reactualizas la lista de alumnos después de la eliminación
            } catch (e: Exception) {
                Log.e("AlumnoViewModel", "Error al eliminar alumno", e)
            }
        }
    }

    fun crearAlumno(alumno: Alumno) {
        viewModelScope.launch {
            try {
                apiService.crearAlumno(alumno)
                _alumno.value = alumno
                obtenerAlumnos() // Reactualizas la lista de alumnos después de la eliminación
            } catch (e: Exception) {
                _alumno.value = null
                Log.e("AlumnoViewModel", "Error creando alumno", e)
            }
        }
    }




}


