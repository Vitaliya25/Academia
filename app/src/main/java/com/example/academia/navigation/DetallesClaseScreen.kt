package com.example.academia.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academia.R
import com.example.academia.model.Clase
import com.example.academia.viewmodel.ClaseViewModel
import com.example.academia.viewmodel.ProfesorViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesClaseScreen(
    claseId: Long?,
    navigateToInit: () -> Unit,
    navigateToAlumnos: () -> Unit,
    navigateToProfesores: () -> Unit,
    navigateToPagos: () -> Unit,
    navigateToHorarios: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: ClaseViewModel = viewModel(),
    profesorViewModel: ProfesorViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var mostrarConfirmacion by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

    val items = listOf(
        "Alumnos" to navigateToAlumnos,
        "Profesores" to navigateToProfesores,
        "Horarios" to navigateToHorarios,
        "Pagos" to navigateToPagos
    )


    val fondoColor = colorResource(id = R.color.colorHorario)

    val clase by viewModel.clase.collectAsState()
    Log.d("DetallesClaseScreen", "Clase cargada: $clase")

    val profesores by profesorViewModel.profesores.collectAsState()


    LaunchedEffect(claseId) {
        Log.d("DetallesClaseScreen", "Cargando clase con id: $claseId")
        if (claseId != 0L) {
            viewModel.obtenerClasePorId(claseId!!)
            profesorViewModel.obtenerProfesores()
        } else {
            Log.d("DetallesClaseScreen", "ClaseId es 0, no se cargan los datos.")
        }
    }

    var asignatura by remember { mutableStateOf(clase?.asignatura ?: "") }
    var curso by remember { mutableStateOf(clase?.curso ?: "") }
    var profesorId by remember { mutableStateOf(clase?.profesorId ?: 0L) }

   // var profesorSeleccionado = profesores.find { it.id == profesorId }
   // val nombreProfesor = profesorSeleccionado?.let { "${it.nombre} ${it.apellido}" } ?: "Seleccione un profesor"

    var expandedProfesor by remember { mutableStateOf(false) }

    // Si la clase no existe, restablecer los campos
    if (claseId == 0L) {
        asignatura = ""
        curso = ""
        profesorId = 0L
    } else if (clase != null) {
        LaunchedEffect(clase) {
            clase?.let {
                asignatura = it.asignatura
                curso = it.curso
                profesorId = it.profesorId ?: 0L
            }
        }

    }

    var confirmarEliminar by remember { mutableStateOf(false) }

    ConfirmarEliminacion(
        showDialog = confirmarEliminar,
        onConfirm = {
            clase?.let {
                viewModel.eliminarClase(it.id!!) // Forzamos que id no sea null
                navigateBack()
            }
            confirmarEliminar = false
        },
        onDismiss = { confirmarEliminar = false }
    )


    fun camposValidos(): Boolean {
        return asignatura.isNotBlank() && curso.isNotBlank() //&& profesorId != 0L
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                items = items,
                backgroundColor = fondoColor,
                modifier = Modifier.padding(6.dp)
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "DETALLE CLASE",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = fondoColor,
                        titleContentColor = colorResource(id = R.color.textoBlanco),
                        navigationIconContentColor = colorResource(id = R.color.fondoClaro),
                        actionIconContentColor = colorResource(id = R.color.textoBlanco)
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navigateBack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Volver",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navigateToInit() }) {
                            Icon(
                                imageVector = Icons.Outlined.Logout,
                                contentDescription = "Salir",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = fondoColor
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Menú",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco)
                            )
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            if (camposValidos()) {
                                if (claseId == 0L) {
                                    val profesorSeleccionado = profesores.find { it.id == profesorId }

                                    val nuevaClase = Clase(
                                        asignatura = asignatura,
                                        curso = curso,
                                        //profesorId = profesorId
                                        profesorId = profesorSeleccionado?.id ?: 0L,
                                        profesor = profesorSeleccionado
                                    )
                                    Log.d("DetallesClaseScreen", "Profesor actualizado en clase: ${profesorSeleccionado?.id}")

                                    viewModel.crearClase(nuevaClase)
                                    mensaje = "¡Clase creada con éxito!"
                                    mostrarConfirmacion = true
                                } else {
                                    val profesorSeleccionado = profesores.find { it.id == profesorId }

                                    clase?.let {
                                        val actualizado = it.copy(
                                            asignatura = asignatura,
                                            curso = curso,
                                            //profesorId = profesorId
                                            //profesorId = profesorSeleccionado?.id ?: it.profesorId
                                            profesor = profesorSeleccionado
                                        )

                                        Log.d("DetallesClaseScreen", "Profesor actualizado en clase: ${profesorSeleccionado?.id}")

                                        viewModel.actualizarClase(actualizado)

                                        mensaje = "¡Cambios guardados con éxito!"
                                        mostrarConfirmacion = true
                                    }

                                }

                            } else {
                                mensaje = "Asignatura, curso, y profesor son obligatorios"
                                mostrarConfirmacion = true
                            }
                        },
                        icon = {
                            Text(
                                text = "Guardar",
                                fontSize = 24.sp,
                                color = colorResource(id = R.color.textoBlanco)
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { confirmarEliminar = true },
                        icon = {
                            Text(
                                text = "Eliminar",
                                fontSize = 24.sp,
                                color = colorResource(id = R.color.textoBlanco)
                            )
                        }
                    )
                }
            }
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(colorResource(id = R.color.fondoClaro))
            ) {
                LaunchedEffect(mostrarConfirmacion) {
                    if (mostrarConfirmacion) {
                        delay(3000)
                        mostrarConfirmacion = false
                    }
                }

                MostrarMensajeConfirmacion(
                    mensaje = mensaje,
                    mostrar = mostrarConfirmacion,
                )

                if (clase != null || claseId == 0L) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = asignatura, onValueChange = { asignatura = it },
                            label = { Text("Asignatura") },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 24.sp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = curso, onValueChange = { curso = it },
                            label = { Text("Curso") },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 24.sp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ExposedDropdownMenuBox(
                            expanded = expandedProfesor,
                            onExpandedChange = { expandedProfesor = !expandedProfesor }
                        ) {
                            val profesorSeleccionado = profesores.find { it.id == profesorId }
                            val nombreProfesor = profesorSeleccionado?.let { "${it.nombre} ${it.apellido}" } ?: "Seleccione un profesor"


                            OutlinedTextField(
                                value = nombreProfesor,
                                onValueChange = {},
                                readOnly = true,  // Impide edición directa
                                label = { Text("Profesor") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProfesor)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                textStyle = TextStyle(
                                    fontSize = 24.sp,
                                    color = androidx.compose.ui.graphics.Color.Black
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedProfesor,
                                onDismissRequest = { expandedProfesor = false }
                            ) {
                                profesores.forEach { profesor ->
                                    DropdownMenuItem(
                                        text = { Text("${profesor.nombre} ${profesor.apellido}") },
                                        onClick = {
                                            profesorId = profesor.id!!
                                            expandedProfesor = false
                                            Log.d("DetallesClaseScreen", "Profesor seleccionado: $profesorId")

                                        }
//                                        onClick = {
//                                            viewModel.actualizarProfesorId(profesor.id!!)
//                                            expandedProfesor = false
//                                        }

                                    )
                                }
                            }
                        }


                        Spacer(modifier = Modifier.height(16.dp))
                    }
                } else {
                    Text(
                        text = "Clase no encontrada",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}
