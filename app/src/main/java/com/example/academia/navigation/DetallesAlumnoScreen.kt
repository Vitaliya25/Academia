package com.example.academia.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academia.R
import com.example.academia.model.Alumno
import com.example.academia.viewmodel.AlumnoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesAlumnoScreen(alumnoId: Long?,
    navigateToInit: () -> Unit,
    navigateToAlumnos: () -> Unit,
    navigateToProfesores: () -> Unit,
    navigateToPagos: () -> Unit,
    navigateToHorarios: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: AlumnoViewModel = viewModel() // Usamos el ViewModel para obtener los datos
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var mostrarConfirmacion by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

    val opcionesCurso = listOf(
        "1º de Secundaria",
        "2º de Secundaria",
        "3º de Secundaria",
        "4º de Secundaria",
        "1º de Bachiller",
        "2º de Bachiller"
    )

    var expanded by remember { mutableStateOf(false) }

    val items = listOf(
        "Alumnos" to navigateToAlumnos,
        "Profesores" to navigateToProfesores,
        "Horarios" to navigateToHorarios,
        "Pagos" to navigateToPagos
    )
    val fondoColor = colorResource(id = R.color.colorAlumno) // Define el color que deseas


    // Llamada al ViewModel para obtener los alumnos
    LaunchedEffect(alumnoId) {
        if (alumnoId != 0L) {
            alumnoId?.let { viewModel.obtenerAlumnoPorId(it) }
        }
    }

    val alumno by viewModel.alumno.collectAsState()

    var nombre by remember { mutableStateOf(alumno?.nombre ?: "") }
    var apellido by remember { mutableStateOf(alumno?.apellido ?: "") }
    var edad by remember { mutableStateOf(alumno?.edad?.toString() ?: "") }
    var curso by remember { mutableStateOf(alumno?.curso ?: "") }
    var telefono by remember { mutableStateOf(alumno?.telefono ?: "") }
    var email by remember { mutableStateOf(alumno?.email ?: "") }

    if (alumnoId == 0L) {
        nombre = ""
        apellido = ""
        edad = ""
        curso = ""
        telefono = ""
        email = ""
    }else

    if (alumnoId != 0L && alumno != null) {
        LaunchedEffect(alumno) {
            alumno?.let {
                nombre = it.nombre
                apellido = it.apellido
                edad = it.edad.toString()
                curso = it.curso
                telefono = it.telefono
                email = it.email
            }
        }
    }
    var confirmarEliminar by remember { mutableStateOf(false) }

    // Confirmación antes de eliminar
    ConfirmarEliminacion(
        showDialog = confirmarEliminar,
        onConfirm = {
            alumno?.let {
                viewModel.eliminarAlumno(it.id) // Llama a la función del ViewModel para eliminar el alumno
                navigateBack() // Regresa a la pantalla anterior después de eliminar
            }
            confirmarEliminar = false // Cierra el diálogo
        },
        onDismiss = { confirmarEliminar = false } // Si se cancela, cierra el diálogo
    )

    fun camposValidos(): Boolean {
        return nombre.isNotBlank() && apellido.isNotBlank() && edad.isNotBlank() &&
                curso.isNotBlank() && telefono.isNotBlank() && email.isNotBlank()
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
                            text = "DETALLE ALUMNO",
                            modifier = Modifier
                                .fillMaxWidth() ,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.colorAlumno),
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
                                imageVector = Icons.Outlined.Logout, // ✅ Icono de salida
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
                    containerColor = colorResource(id = R.color.colorAlumno)
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.open() // Abre el menú lateral
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
                                if (alumnoId == 0L) {
                                    // Crear nuevo alumno
                                    val nuevoAlumno = Alumno(
                                        //id = 0,  // No es necesario asignar un id aquí, se asignará en el backend
                                        nombre = nombre,
                                        apellido = apellido,
                                        edad = edad.toIntOrNull() ?: 0,
                                        curso = curso,
                                        telefono = telefono,
                                        email = email
                                    )
                                    viewModel.crearAlumno(nuevoAlumno)
                                    mensaje = "¡Alumno creado con éxito!"
                                    mostrarConfirmacion = true

                                } else {

                                    alumno?.let {
                                        val alumnoActualizado = it.copy(
                                            nombre = nombre,
                                            apellido = apellido,
                                            edad = edad.toIntOrNull() ?: it.edad,
                                            curso = curso,
                                            telefono = telefono,
                                            email = email
                                        )
                                        viewModel.actualizarAlumno(alumnoActualizado)
                                        mensaje = "¡Cambios guardados con exito!"
                                        mostrarConfirmacion = true
                                    }
                                }
                            }
                            else {
                                mensaje = "¡Complete todos los campos!"
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
                        onClick = { confirmarEliminar = true }, // Muestra el diálogo cuando el usuario hace clic en "Eliminar"
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
            )
            {

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


                if (alumno != null || alumnoId == 0L) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    {
//                        if (alumno != null) {
//                            Text("ID: ${alumno!!.id}", fontSize = 24.sp)
//                        } else {
//                            Text("ID: -", fontSize = 24.sp)
//                        }

                        Spacer(modifier = Modifier.weight(1f)                                    .fillMaxWidth())
                        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                            value = nombre, onValueChange = { nombre = it },
                            label = { Text("Nombre") },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                            value = apellido, onValueChange = { apellido = it },
                            label = { Text("Apellido") },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                            value = edad, onValueChange = { edad = it },
                            label = { Text("Edad") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = curso,
                                onValueChange = { curso = it },
                                readOnly = true,
                                label = { Text("Curso") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                textStyle = TextStyle(
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opcionesCurso.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            curso = opcion
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }


                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                            value = telefono, onValueChange = { telefono = it },
                            label = { Text("Teléfono") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 24.sp,
                                color = Color.Black,
                               // fontWeight = FontWeight.Bold
                            )
                            )
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                            value = email, onValueChange = { email = it },
                            label = { Text("Email") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 24.sp,
                                color = Color.Black,
                                //fontWeight = FontWeight.Bold
                            )
                            )
                        Spacer(modifier = Modifier.weight(1f))

                    }
                } else {
                    Text(
                        text = "Alumno no encontrado",
                        modifier = Modifier
                            .align(Alignment.Center),
                        color = Color.Red,
                        fontSize = 24.sp
                    )
                }
            }

            }
        }
    }

@Composable
fun ConfirmarEliminacion(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Confirmar Eliminación",
                    fontSize = 24.sp, // Tamaño del texto del título
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.fondoOscuro) // Color del texto del título
                )
            },
            text = {
                Text(
                    text = "¿Estás seguro de que quieres eliminar este alumno?",
                    fontSize = 24.sp, // Tamaño del texto principal
                    color = Color.DarkGray // Color del texto del título
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirm,
                ) {
                    Text(
                        text = "Eliminar",
                        fontSize = 22.sp,
                        color = Color.Red
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss,

                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.fondoOscuro)

                    )
                }
            },
        )
    }
}