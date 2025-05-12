package com.example.academia.navigation

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
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academia.R
import com.example.academia.model.Profesor
import com.example.academia.viewmodel.ClaseViewModel
import com.example.academia.viewmodel.ProfesorViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesProfesorScreen(
    profesorId: Long?,
    navigateToInit: () -> Unit,
    navigateToAlumnos: () -> Unit,
    navigateToProfesores: () -> Unit,
    navigateToPagos: () -> Unit,
    navigateToHorarios: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: ProfesorViewModel = viewModel()
) {
    //val claseViewModel: ClaseViewModel = viewModel()

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

    val fondoColor = colorResource(id = R.color.colorProfesor)

    LaunchedEffect(profesorId) {
        if (profesorId != 0L) {
            profesorId?.let { viewModel.obtenerProfesorPorId(it) }
        }
    }

    val profesor by viewModel.profesor.collectAsState()

    var nombre by remember { mutableStateOf(profesor?.nombre ?: "") }
    var apellido by remember { mutableStateOf(profesor?.apellido ?: "") }
    // var asignatura by remember { mutableStateOf(profesor?.asignatura ?: "") }
    var telefono by remember { mutableStateOf(profesor?.telefono ?: "") }
    var email by remember { mutableStateOf(profesor?.email ?: "") }

    if (profesorId == 0L) {
        nombre = ""
        apellido = ""
        //    asignatura = ""
        telefono = ""
        email = ""
    } else if (profesor != null) {
        LaunchedEffect(profesor) {
            profesor?.let {
                nombre = it.nombre
                apellido = it.apellido
                //     asignatura = it.asignatura
                telefono = it.telefono
                email = it.email
            }
        }
    }

    var confirmarEliminar by remember { mutableStateOf(false) }

    ConfirmarEliminacion(
        showDialog = confirmarEliminar,
        onConfirm = {
            profesor?.let {
                viewModel.eliminarProfesor(it.id)
                navigateBack()
            }
            confirmarEliminar = false
        },
        onDismiss = { confirmarEliminar = false }
    )

    fun camposValidos(): Boolean {
        return nombre.isNotBlank() && apellido.isNotBlank()
                && telefono.isNotBlank() && email.isNotBlank()
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
                            text = "DETALLE PROFESOR",
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
                                if (profesorId == 0L) {
                                    val nuevoProfesor = Profesor(
                                        nombre = nombre,
                                        apellido = apellido,
                                        telefono = telefono,
                                        email = email
                                    )
                                    viewModel.crearProfesor(nuevoProfesor)
                                    mensaje = "¡Profesor creado con éxito!"
                                    mostrarConfirmacion = true
                                } else {
                                    profesor?.let {
                                        val actualizado = it.copy(
                                            nombre = nombre,
                                            apellido = apellido,
                                            telefono = telefono,
                                            email = email,
                                            clases = it.clases
                                        )

                                        viewModel.actualizarProfesor(actualizado)
                                        mensaje = "¡Cambios guardados con exito!"
                                        mostrarConfirmacion = true
                                    }
                                }
                            } else {
                                mensaje = "Nombre, apellido, telefono, email - obligatorios"
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

                if (profesor != null || profesorId == 0L) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = nombre, onValueChange = { nombre = it },
                            label = { Text("Nombre") },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 24.sp, color = Color.Black)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = apellido, onValueChange = { apellido = it },
                            label = { Text("Apellido") },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 24.sp, color = Color.Black)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = telefono, onValueChange = { telefono = it },
                            label = { Text("Teléfono") },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 24.sp, color = Color.Black)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email, onValueChange = { email = it },
                            label = { Text("Email") },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 24.sp, color = Color.Black)
                        )

//                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = {
                            mensaje = "Para modificar clases asignadas:  Menu / Horarios"
                            mostrarConfirmacion = true
                        }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Filled.Help,
                                tint = colorResource(id = R.color.colorProfesor),
                                contentDescription = "Ayuda"
                            )
                        }

                        Text("Clases asignadas:", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))

                        if (profesor != null) {
                            profesor?.clases?.forEach { clase ->
                                Text(
                                    text = "  · ${clase.asignatura} ${clase.curso}",
                                    fontSize = 22.sp,
                                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                                )
                            }

                        }

                        Spacer(modifier = Modifier.weight(1f))

                    }
                } else {
                    Text(
                        text = "Profesor no encontrado",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MostrarMensajeConfirmacion(
    mensaje: String,
    mostrar: Boolean,

    ) {
    if (mostrar) {
        Card(
            modifier = Modifier.padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = mensaje,
                modifier = Modifier.padding(6.dp),
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
}