package com.example.academia.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.model.Alumno
import com.example.academia.model.Pago
import com.example.academia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PagoViewModel: ViewModel() {
    private val _pagos = MutableStateFlow<List<Pago>>(emptyList())
    val pagos: StateFlow<List<Pago>> = _pagos.asStateFlow()

    private val _pago = MutableStateFlow<Pago?>(null)
    val pago: StateFlow<Pago?> = _pago

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    private val apiService = RetrofitInstance.pagoApi
    private var todosLosPagos: List<Pago> = emptyList()


    fun obtenerPagos() {
        viewModelScope.launch {
            try {
                val pagosApi = apiService.getTodosLosPagos()
                todosLosPagos = pagosApi
                _pagos.value = pagosApi
                println("Pagos cargados desde ViewModel: $pagosApi")
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener los pagos: ${e.message}"
            }
        }
    }

    fun obtenerPagosPorId(id: Long) {
        viewModelScope.launch {
            try {
                _pago.value = apiService.getPagoPorId(id)  // <<< AQUÍ ACTUALIZAS
                _error.value = null
            } catch (e: Exception) {
                _pago.value = null
                _error.value = "Error buscando pagos: ${e.message}"
            }
        }
    }


    fun crearPago(pago: Pago) {
        viewModelScope.launch {
            try {
                apiService.crearPago(pago)
                _pago.value = pago
                obtenerPagos() // Actualizar lista después de crear
            } catch (e: Exception) {
                _pago.value = null
                _error.value = "Error creando clase: ${e.message}"
            }
        }
    }

    fun generarPagosParaTodosLosAlumnos(
        alumnos: List<Alumno>,
        mes: String,
        anio: String,
        cantidad: Long = 10000L
    ) {
        viewModelScope.launch {
            try {
                alumnos.forEach { alumno ->
                    val nuevoPago = Pago(
                        fechaMensualidad = "$anio-$mes-01",
                        cantidad = cantidad,
                        fechaPago = "", // Pendiente
                        alumnoId = alumno.id,
                        alumno = alumno
                    )
                    crearPago(nuevoPago) // Reutiliza tu función existente
                }
                obtenerPagos()
            } catch (e: Exception) {
                _error.value = "Error generando pagos masivos: ${e.message}"
            }
        }
    }


    fun actualizarPago(pago: Pago) {
        viewModelScope.launch {
            try {
                val id = pago.id ?: throw IllegalArgumentException("El id del pago no puede ser nulo al actualizar")
                val pagoActualizado = apiService.actualizarPago(id, pago)
                _pago.value = pagoActualizado
                obtenerPagos()
                Log.d("ClaseViewModel", "Actualizando clase VM After: ${pagoActualizado}")
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error actualizando clase: ${e.message}"
            }
        }
    }


    fun eliminarPago(id: Long) {
        viewModelScope.launch {
            try {
                apiService.eliminarPago(id)
                obtenerPagos()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error eliminando clase: ${e.message}"
            }
        }
    }
}
