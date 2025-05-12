package com.example.academia.navigation

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academia.R
import com.example.academia.model.Pago
import com.example.academia.viewmodel.AlumnoViewModel
import com.example.academia.viewmodel.PagoViewModel
import com.example.academia.utils.MESES
import com.example.academia.utils.ANIOS
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPagosScreen(navigateToInit: () -> Unit,
                       navigateToAlumnos: () -> Unit,
                       navigateToProfesores: () -> Unit,
                       navigateToPagos: () -> Unit,
                       navigateToHorarios: () -> Unit,
                       navigateBack: () -> Unit,
                       viewModel: PagoViewModel = viewModel(),
                       viewModelA: AlumnoViewModel = viewModel(),

                       ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val items = listOf(
        "Alumnos" to navigateToAlumnos,
        "Profesores" to navigateToProfesores,
        "Horarios" to navigateToHorarios,
        "Pagos" to navigateToPagos
    )
    val fondoColor = colorResource(id = R.color.colorPago)

    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.obtenerPagos()
    }
    val pagos by viewModel.pagos.collectAsState()

    LaunchedEffect(Unit) {
        viewModelA.obtenerAlumnos()
    }
    val alumnos by viewModelA.alumnos.collectAsState()

    var filtroMes by remember { mutableStateOf(MESES[0]) } // Pair("Todos", "")
    var filtroAnio by remember { mutableStateOf(ANIOS[0]) }
    var filtroAlumno by remember { mutableStateOf("") }

    var expandedMes by remember { mutableStateOf(false) }
    var expandedAnio by remember { mutableStateOf(false) }

    var filtroEstadoPago by remember { mutableStateOf("Todos") } // "Todos", "Pagado", "Pendiente"

    var pagoSeleccionado by remember { mutableStateOf<Pago?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    var mostrarDialogoGenerarPagos by remember { mutableStateOf(false) }
    var mostrarDialogoAdvertencia by remember { mutableStateOf(false) }

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
                            text = "PAGOS",
                            modifier = Modifier
                                .fillMaxWidth() ,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.colorPago),
                        titleContentColor = colorResource(id = R.color.textoBlanco),
                        navigationIconContentColor = colorResource(id = R.color.fondoClaro),
                        actionIconContentColor = colorResource(id = R.color.textoBlanco)
                    ),

                    navigationIcon = {
                        IconButton(
                            onClick = { navigateBack() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Volver",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { navigateToInit() }
                        ) {
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
                    containerColor = colorResource(id = R.color.colorPago) // Colores de fondo
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                        icon = {
                            Icon(Icons.Filled.Menu,
                                contentDescription = "Menú",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco))
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            mostrarDialogoGenerarPagos = true
                        },
                        icon = {
                            Text(
                                text = "Generar",
                                fontSize = 24.sp,
                                color = colorResource(id = R.color.textoBlanco),
                                modifier = Modifier
                                    .clickable { mostrarDialogoGenerarPagos = true }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (pagos.isEmpty()) {
                    Text("No se han encontrado pagos ", modifier = Modifier.align(Alignment.Center))
                }
                Column(modifier = Modifier.fillMaxSize()) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.colorPagoClaro))
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Ver Todos",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .clickable {
                                    filtroAlumno = ""
                                    filtroMes = MESES[0]
                                    filtroAnio = ANIOS[0]
                                    filtroEstadoPago = "Todos"
                                    viewModel.obtenerPagos()
                                }

                                .padding(horizontal = 12.dp)
                        )
                    }


                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Pagados
                            TextButton(
                                onClick = { filtroEstadoPago = "Pagado" }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Pagados",
                                    tint = colorResource(id = R.color.pagado) ,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Pagados",
                                    fontSize = 24.sp,
                                    color = Color.Black)
                            }

                            TextButton(
                                onClick = { filtroEstadoPago = "Pendiente" },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "Pendientes",
                                    tint = colorResource(id = R.color.pendiente),
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Pendientes",
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                            }

                        }


                        OutlinedTextField(
                            value = filtroAlumno,
                            onValueChange = { filtroAlumno = it },
                            label = { Text("Nombre/Apellido", fontSize = 20.sp, fontWeight = FontWeight.Normal) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.DarkGray,  // Aquí cambias el color del texto que se escribe
                                fontSize = 22.sp       // Aquí cambias el tamaño del texto
                            ),
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

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Contenedor con weight para "Mes"
                            Box(modifier = Modifier.weight(1f)) {
                                ExposedDropdownMenuBox(
                                    expanded = expandedMes,
                                    onExpandedChange = { expandedMes = !expandedMes }
                                ) {
                                    OutlinedTextField(
                                        value = filtroMes.first,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Mes", fontSize = 20.sp, fontWeight = FontWeight.Normal) },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMes)
                                        },
                                        modifier = Modifier.menuAnchor().fillMaxWidth(),
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

                                    ExposedDropdownMenu(
                                        expanded = expandedMes,
                                        onDismissRequest = { expandedMes = false }
                                    ) {
                                        MESES.forEach { mes ->
                                            DropdownMenuItem(
                                                text = { Text(mes.first) },
                                                onClick = {
                                                    filtroMes = mes
                                                    expandedMes = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            // Contenedor con weight para "Año"
                            Box(modifier = Modifier.weight(1f)) {
                                ExposedDropdownMenuBox(
                                    expanded = expandedAnio,
                                    onExpandedChange = { expandedAnio = !expandedAnio }
                                ) {
                                    OutlinedTextField(
                                        value = filtroAnio,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Año", fontSize = 20.sp, fontWeight = FontWeight.Normal) },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAnio)
                                        },
                                        modifier = Modifier.menuAnchor().fillMaxWidth(),
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

                                    ExposedDropdownMenu(
                                        expanded = expandedAnio,
                                        onDismissRequest = { expandedAnio = false }
                                    ) {
                                        ANIOS.forEach { anio ->
                                            DropdownMenuItem(
                                                text = { Text(anio) },
                                                onClick = {
                                                    filtroAnio = anio
                                                    expandedAnio = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val pagosFiltrados = pagos.filter { pago ->
                        val nombreCompleto = "${pago.alumno?.nombre.orEmpty()} ${pago.alumno?.apellido.orEmpty()}".lowercase()
                        val coincideNombre = filtroAlumno.isBlank() || nombreCompleto.contains(filtroAlumno.lowercase())

                        val fechaMensualidad = pago.fechaMensualidad
                        val mes = if (fechaMensualidad.length >= 7) fechaMensualidad.substring(5, 7) else ""
                        val anio = if (fechaMensualidad.length >= 4) fechaMensualidad.substring(0, 4) else ""

                        val coincideMes = filtroMes.second.isBlank() || mes == filtroMes.second
                        val coincideAnio = filtroAnio == "Todos" || anio == filtroAnio

                        val coincideEstado = when (filtroEstadoPago) {
                            "Pagado" -> !pago.fechaPago.isNullOrBlank()
                            "Pendiente" -> pago.fechaPago.isNullOrBlank()
                            else -> true
                        }

                        coincideNombre && coincideMes && coincideAnio && coincideEstado
                    }



                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(pagosFiltrados) { pago ->
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${pago.id}:",
                                            modifier = Modifier.padding(8.dp),
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                color = Color.DarkGray
                                            )
                                        )
                                        Text(
                                            text = "${pago.alumno?.nombre.orEmpty()} ${pago.alumno?.apellido.orEmpty()} " +
                                                    "(id = ${pago.alumno?.id})",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                color = Color.Black
                                            )
                                        )

                                    }
                                    Text(
                                        text = "Mensualidad: ${pago.fechaMensualidad}",
                                        modifier = Modifier.padding(start = 60.dp),
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = Color.DarkGray
                                        )
                                    )

                                    Text(
                                        text = "Cantidad: ${pago.cantidad}",
                                        modifier = Modifier.padding(start = 60.dp),
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = Color.DarkGray
                                        )
                                    )
                                    Text(
                                        text = "Pagado: ${pago.fechaPago ?: "Pendiente"}",
                                        modifier = Modifier.padding(start = 60.dp),
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = Color.DarkGray
                                        )
                                    )

                                    if (pago.fechaPago.isNullOrBlank()) {
                                        TextButton(
                                            onClick = {
                                                pagoSeleccionado = pago
                                                mostrarDialogo = true
                                            }
                                        ) {
                                            Text("Aceptar pago",
                                                style = TextStyle(
                                                    fontSize = 20.sp,
                                                    color = colorResource(id = R.color.aceptarPago)
                                                )
                                            )
                                        }
                                    }




                                }
                            }
                        }
                    }

                    if (mostrarDialogo && pagoSeleccionado != null) {
                        AlertDialog(
                            onDismissRequest = {
                                mostrarDialogo = false
                                pagoSeleccionado = null
                            },
                            title = {
                                Text(
                                text = "Confirmar Pago",
                                fontSize = 24.sp, // Tamaño del texto del título
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(id = R.color.fondoOscuro) // Color del texto del título
                            )},
                            text = {
                                Text(
                                    text = "¿Deseas marcar este pago como realizado?",
                                    fontSize = 24.sp, // Tamaño del texto principal
                                    color = Color.DarkGray // Color del texto del título
                                )
                                   },
                            confirmButton = {
                                TextButton(onClick = {
                                    val pagoActualizado = pagoSeleccionado!!.copy(
                                        fechaPago = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                    )
                                    viewModel.actualizarPago(pagoActualizado)
                                    mostrarDialogo = false
                                    pagoSeleccionado = null
                                }) {
                                    Text(
                                        text = "Si",
                                        fontSize = 22.sp,
                                        color = colorResource(id = R.color.aceptarPago)                                   )
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    mostrarDialogo = false
                                    pagoSeleccionado = null
                                }) {
                                    Text(
                                        text = "Cancelar",
                                        fontSize = 22.sp,
                                        color = colorResource(id = R.color.fondoOscuro)

                                    )
                                }
                            }
                        )
                    }

                    if (mostrarDialogoGenerarPagos) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialogoGenerarPagos = false },
                            title = { Text("Confirmar generación de pagos",
                                fontSize = 24.sp, // Tamaño del texto del título
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(id = R.color.fondoOscuro) // Color del texto del título
                            ) },
                            text = { Text("¿Deseas generar pagos para todos los alumnos?",
                                fontSize = 24.sp, // Tamaño del texto principal
                                color = Color.DarkGray // Color del texto del título
                            ) },
                            confirmButton = {
                                TextButton(onClick = {
                                    val mesSeleccionado = filtroMes.second
                                    val anioSeleccionado = filtroAnio

                                    if (mesSeleccionado.isBlank() || anioSeleccionado == "Todos") {
                                        mostrarDialogoAdvertencia = true
                                    } else {
                                        viewModel.generarPagosParaTodosLosAlumnos(
                                            alumnos = alumnos,
                                            mes = mesSeleccionado,
                                            anio = anioSeleccionado
                                        )
                                        mostrarDialogoGenerarPagos = false
                                    }
                                }) {
                                    Text("Sí",
                                        fontSize = 22.sp,
                                        color = colorResource(id = R.color.aceptarPago)
                                    )
                                }
                            },

                                    dismissButton = {
                                TextButton(onClick = {
                                    mostrarDialogoGenerarPagos = false
                                }) {
                                    Text("Cancelar",
                                        fontSize = 22.sp,
                                        color = colorResource(id = R.color.fondoOscuro)
                                        )
                                }
                            }
                        )
                    }

                    if (mostrarDialogoAdvertencia) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialogoAdvertencia = false },
                            title = { Text("Faltan datos",
                                fontSize = 24.sp, // Tamaño del texto del título
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(id = R.color.fondoOscuro) // Color del texto del título
                            ) },
                            text = { Text("Debes seleccionar un mes y un año antes de generar los pagos.",
                                fontSize = 24.sp, // Tamaño del texto principal
                                color = Color.DarkGray // Color del texto del título
                            ) },
                            confirmButton = {
                                TextButton(onClick = { mostrarDialogoAdvertencia = false }) {
                                    Text("Aceptar",
                                        fontSize = 22.sp,
                                        color = colorResource(id = R.color.aceptarPago)
                                    )
                                }
                            }
                        )
                    }

                }

            }
        }
    }
}
