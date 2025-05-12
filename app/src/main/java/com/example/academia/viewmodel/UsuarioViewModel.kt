package com.example.academia.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.model.Usuario
import com.example.academia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios.asStateFlow()

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val apiService = RetrofitInstance.usuarioApi
    private val _error = mutableStateOf<String?>(null)

    //val error: State<String?> get() = _error

    fun obtenerUsuarios() {
        viewModelScope.launch {
            try {
                val usuariosApi = apiService.obtenerUsuarios()
                _usuarios.value = usuariosApi
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al obtener los usuarios: ${e.message}"
            }
        }
    }

//    fun obtenerUsuarioPorUsername(username: String) {
//        viewModelScope.launch {
//            try {
//                val usuario = apiService.obtenerUsuarioPorUsername(username)
//                _usuario.value = usuario
//                _error.value = null
//            } catch (e: Exception) {
//                _error.value = "Error al obtener el usuario: ${e.message}"
//            }
//        }
//    }

    fun crearUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                apiService.crearUsuario(usuario)
                obtenerUsuarios()
                _usuario.value = usuario
            } catch (e: Exception) {
                _error.value = "Error al crear el usuario: ${e.message}"
            }
        }
    }

    fun actualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                apiService.actualizarUsuario(usuario.id!!, usuario)
                _usuario.value = usuario
                obtenerUsuarios()
            } catch (e: Exception) {
                _error.value = "Error al actualizar el usuario: ${e.message}"
            }
        }
    }

    fun eliminarUsuario(id: Long?) {
        viewModelScope.launch {
            try {
                if (id != null) {
                    apiService.eliminarUsuario(id)
                    obtenerUsuarios()
                }
            } catch (e: Exception) {
                _error.value = "Error al eliminar el usuario: ${e.message}"
            }
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
//                val response = apiService.login(mapOf("email" to username, "password" to password))
                val response = apiService.login(mapOf("username" to username, "password" to password))

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            } catch (e: Exception) {
                onError()
            }
        }
    }
}
