package com.example.academia.navigation

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academia.R
import com.example.academia.model.Clase
import com.example.academia.model.Profesor
import com.example.academia.viewmodel.ClaseViewModel
import com.example.academia.viewmodel.HorarioViewModel
import com.example.academia.viewmodel.ProfesorViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionHorariosScreen(
    navigateToInit: () -> Unit,
    navigateToAlumnos: () -> Unit,
    navigateToProfesores: () -> Unit,
    navigateToPagos: () -> Unit,
    navigateToHorarios: () -> Unit,
    navigateBack: () -> Unit,
    navigateToDetalle: (Long?) -> Unit,
    viewModelH: HorarioViewModel = viewModel(),
    viewModelC: ClaseViewModel = viewModel(),
    viewModelP: ProfesorViewModel = viewModel(),
    ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val items = listOf(
        "Alumnos" to navigateToAlumnos,
        "Profesores" to navigateToProfesores,
        "Horarios" to navigateToHorarios,
        "Pagos" to navigateToPagos
    )

    var expanded by remember { mutableStateOf(false) }
    var cursoFiltro by remember { mutableStateOf("") }

    val opcionesCurso = listOf(
        "1º de Secundaria",
        "2º de Secundaria",
        "3º de Secundaria",
        "4º de Secundaria",
        "1º de Bachiller",
        "2º de Bachiller"
    )

    val fondoColor = colorResource(id = R.color.colorHorario) // Define el color que deseas

    var selectedFilter by remember { mutableStateOf("Clases") }
    var filtroAsignatura by remember { mutableStateOf("") }
    var filtroCurso by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModelH.obtenerHorarios()
    }
    val horarios by viewModelH.horarios.collectAsState()

    LaunchedEffect(Unit) {
        viewModelC.obtenerClases()
    }
    val clases by viewModelC.clases.collectAsState()
    Log.d("GestionHorariosScreen", "Clases GHS:  $clases")


    LaunchedEffect(Unit) {
        viewModelP.obtenerProfesores()
    }
    val profesores by viewModelP.profesores.collectAsState()


    var nombreProfesorFiltro by remember { mutableStateOf("") }


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
                            text = "HORARIOS",
                            modifier = Modifier
                                .fillMaxWidth() ,
                            textAlign = TextAlign.Center                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.colorHorario),
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
                    containerColor = colorResource(id = R.color.colorHorario) // Colores de fondo
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
                            ) // Color del icono
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            // Acción para el botón Home
                        },
                        icon = {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Nueva Clase",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco)
                            ) // Color del icono
                        }
                    )

                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(colorResource(id = R.color.fondoClaro)),
//                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.colorHorarioClaro))
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("Clases","Profesores", "Horarios").forEach { filtro ->
                            Text(
                                text = filtro,
                                fontSize = 24.sp,
                                fontWeight = if (selectedFilter == filtro) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedFilter == filtro) Color.DarkGray else colorResource(
                                    id = R.color.textoBlanco
                                ),
                                modifier = Modifier
                                    .clickable { selectedFilter = filtro }
                                    .padding(horizontal = 12.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    ) {

                        when (selectedFilter) {
                            "Clases" -> FiltroClasesSection(
                                clases = clases,
                                profesores = profesores,
                                cursoFiltro = cursoFiltro,
                                filtroAsignatura = filtroAsignatura,
                                opcionesCurso = opcionesCurso,
                                onAsignaturaChange = { filtroAsignatura = it },
                                onCursoSeleccionado = {
                                    cursoFiltro = it
                                    filtroCurso = it
                                },
                                onVerTodos = {
                                    filtroAsignatura = ""
                                    filtroCurso = ""
                                    cursoFiltro = ""
                                },
                                navigateToDetalle = navigateToDetalle
                            )

                            "Profesores" -> FiltroProfesoresSection(
                                clases = clases,
                                profesores = profesores,
                                nombreProfesorFiltro = nombreProfesorFiltro,
                                onNombreChange = { nombreProfesorFiltro = it },
                                onVerTodos = { nombreProfesorFiltro = "" },
                                navigateToDetalle = navigateToDetalle
                            )

                            "Horarios" -> { /* Lógica pendiente */ }
                        }


                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }
            }
        }
    }


}

@Composable
fun ClaseList(clases: List<Clase>, profesores: List<Profesor>, navigateToDetalle: (Long?) -> Unit,) {
    LazyColumn {
        items(clases) { clase ->
            val profesorNombre = profesores
                .find { it.id == clase.profesorId }
                ?.let { "${it.nombre} ${it.apellido}" }
                ?: "Desconocido"

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                      navigateToDetalle(clase.id)
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Text(text = "Asignatura: ",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold)
                        Text(text = "   ${clase.asignatura}",
                            fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Curso:",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "    ${clase.curso}",
                            fontSize = 22.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Profesor:",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "    $profesorNombre",
                            fontSize = 22.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        Column {
                            Text(
                                text = "Horarios:",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            clase.horarios.forEach { horario ->
                                val horaInicioFormatted =
                                    horario.horaInicio.take(5) // toma los primeros 5 caracteres "HH:mm"
                                val horaFinFormatted = horario.horaFin.take(5)
                                Text(
                                    text = "${horario.dia} - $horaInicioFormatted a $horaFinFormatted",
                                    fontSize = 22.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltroClasesSection(
    clases: List<Clase>,
    profesores: List<Profesor>,
    cursoFiltro: String,
    filtroAsignatura: String,
    opcionesCurso: List<String>,
    onAsignaturaChange: (String) -> Unit,
    onCursoSeleccionado: (String) -> Unit,
    onVerTodos: () -> Unit,
    navigateToDetalle: (Long?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {

        OutlinedButton(
            onClick = onVerTodos,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black),
            shape = RectangleShape
        ) {
            Text("Ver Todos", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        OutlinedTextField(
            value = filtroAsignatura,
            onValueChange = onAsignaturaChange,
            label = { Text("Asignatura", fontSize = 20.sp, fontWeight = FontWeight.Normal) },
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

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = cursoFiltro,
                onValueChange = {},
                readOnly = true,
                label = { Text("Curso", fontSize = 20.sp, fontWeight = FontWeight.Normal) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
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
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcionesCurso.forEach { curso ->
                    DropdownMenuItem(
                        text = { Text(curso) },
                        onClick = {
                            onCursoSeleccionado(curso)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val clasesFiltradas = clases.filter {
            it.asignatura.contains(filtroAsignatura, ignoreCase = true) &&
                    it.curso.contains(cursoFiltro, ignoreCase = true)
        }

        ClaseList(clases = clasesFiltradas, profesores = profesores, navigateToDetalle = navigateToDetalle)
    }
}

@Composable
fun FiltroProfesoresSection(
    clases: List<Clase>,
    profesores: List<Profesor>,
    nombreProfesorFiltro: String,
    onNombreChange: (String) -> Unit,
    onVerTodos: () -> Unit,
    navigateToDetalle: (Long?) -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedButton(
            onClick = onVerTodos,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black),
            shape = RectangleShape
        ) {
            Text("Ver Todos", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = nombreProfesorFiltro,
            onValueChange = onNombreChange,
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

        Spacer(modifier = Modifier.height(8.dp))

        val clasesFiltradas = if (nombreProfesorFiltro.isBlank()) clases else {
            clases.filter { clase ->
                profesores.find { it.id == clase.profesorId }
                    ?.let { "${it.nombre} ${it.apellido}".contains(nombreProfesorFiltro, ignoreCase = true) } == true
            }
        }

        if (clasesFiltradas.isEmpty()) {
            Text("No se encontraron clases para este profesor", modifier = Modifier.padding(8.dp))
        } else {
            ClaseList(clases = clasesFiltradas, profesores = profesores, navigateToDetalle)
        }
    }
}
