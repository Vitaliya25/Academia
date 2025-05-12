package com.example.academia.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
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
import com.example.academia.R
import com.example.academia.viewmodel.AlumnoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionAlumnosScreen(
    navigateToInit: () -> Unit,
    navigateToAlumnos: () -> Unit,
    navigateToProfesores: () -> Unit,
    navigateToPagos: () -> Unit,
    navigateToHorarios: () -> Unit,
    navigateBack: () -> Unit,
    navigateToDetalle: (Long?) -> Unit,
    viewModel: AlumnoViewModel = viewModel() // Usamos el ViewModel para obtener los datos
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var nombreFiltro by remember { mutableStateOf("") }

    val items = listOf(
        "Alumnos" to navigateToAlumnos,
        "Profesores" to navigateToProfesores,
        "Horarios" to navigateToHorarios,
        "Pagos" to navigateToPagos
    )
    val fondoColor = colorResource(id = R.color.colorAlumno) // Define el color que deseas

    LaunchedEffect(Unit) {
        viewModel.obtenerAlumnos()
    }
    val alumnos by viewModel.alumnos.collectAsState()

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
                            text = "ALUMNOS",
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
                            Icon(Icons.Filled.Menu,
                                contentDescription = "Menú",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco)) // Color del icono
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            navigateToDetalle(0)
                        },
                        icon = {
                            Icon(Icons.Filled.PersonAdd,
                                contentDescription = "Añadir alumno",
                                modifier = Modifier.size(32.dp),
                                tint = colorResource(id = R.color.textoBlanco)) // Color del icono
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
                if (alumnos.isEmpty()) {
                    Text("No se han encontrado alumnos ", modifier = Modifier.align(Alignment.Center))
                }
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(colorResource(id = R.color.colorAlumnoClaro))
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
//
                            Text(
                                text = "Ver Todos",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        nombreFiltro = ""
                                        cursoFiltro = ""
                                        viewModel.obtenerAlumnos()
                                    }
                                    .padding(horizontal = 12.dp)
                            )
                        }

                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                        ) {

                                        OutlinedTextField(
                                            value = nombreFiltro,
                                            onValueChange = {
                                                nombreFiltro = it
                                                viewModel.buscarAlumnosCombinado(nombreFiltro, cursoFiltro)
                                            },
                                            label = { Text("Nombre/Apellido", fontSize = 20.sp, fontWeight = FontWeight.Normal) },
                                            modifier = Modifier.fillMaxWidth(),
                                            singleLine = true,
                                            textStyle = TextStyle(
                                                color = Color.DarkGray,  // Aquí cambias el color del texto que se escribe
                                                fontSize = 24.sp       // Aquí cambias el tamaño del texto
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

                                        // 🎓 Búsqueda dinámica por curso
                                        ExposedDropdownMenuBox(
                                            expanded = expanded,
                                            onExpandedChange = { expanded = !expanded }
                                        ) {
                                            OutlinedTextField(
                                                value = cursoFiltro,
                                                onValueChange = { }, // No hace nada porque es readOnly
                                                readOnly = true,
                                                label = { Text("Curso", fontSize = 20.sp, fontWeight = FontWeight.Normal) },
                                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                                                textStyle = TextStyle(
                                                    color = Color.DarkGray,  // Aquí cambias el color del texto que se escribe
                                                    fontSize = 24.sp       // Aquí cambias el tamaño del texto
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .menuAnchor(),
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
                                                opcionesCurso.forEach { opcion ->
                                                    DropdownMenuItem(
                                                        text = { Text(opcion) },
                                                        onClick = {
                                                            cursoFiltro = opcion
                                                            expanded = false
                                                            viewModel.buscarAlumnosCombinado(nombreFiltro, cursoFiltro)
                                                        }
                                                    )

                                                }
                                            }
                                        }
                                    }

                            Spacer(modifier = Modifier.height(16.dp))

                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(alumnos) { alumno ->

                                    Card(
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp, vertical = 4.dp)
                                            .fillMaxWidth()
                                            .clickable {
                                                navigateToDetalle(alumno.id)
                                            },
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),

                                        ) {
                                        Column(modifier = Modifier.fillMaxWidth()
                                            .padding(8.dp),
                                            horizontalAlignment = Alignment.Start,
                                            //horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                            Row (
                                                verticalAlignment = Alignment.CenterVertically,
                                            ){
                                                Text(
                                                    text = "${alumno.id}:",
                                                    modifier = Modifier.padding(8.dp),
                                                    style = TextStyle(
                                                        fontSize = 20.sp,
                                                        color = Color.DarkGray
                                                    )
                                                )

                                                Text(
                                                    text = "${alumno.nombre} ${alumno.apellido}",
                                                    style = TextStyle(
                                                        fontSize = 24.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.Black
                                                    )
                                                )
                                            }
                                            Text(
                                                text = alumno.curso,
                                                modifier = Modifier.padding(start = 60.dp),
                                                style = TextStyle(
                                                    fontSize = 20.sp,
                                                    color = Color.DarkGray
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
//        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        color = colorResource(id = R.color.textoBlanco)
    )
}

@Composable
fun DrawerContent(
    items: List<Pair<String, () -> Unit>>,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(200.dp)
            .background(backgroundColor)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Menú",
            modifier = Modifier.padding(18.dp),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.textoBlanco)
        )
        HorizontalDivider(
            Modifier.fillMaxWidth(),
            color = Color.White,
            thickness = 2.dp
        )
        items.forEach { (text, onClick) ->
            DrawerItem(text, onClick)
        }
    }
}