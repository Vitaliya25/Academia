package com.example.academia.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.academia.R
import com.example.academia.model.MenuItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(userRole: String,
                    navigateToInit: () -> Unit,
                    navigateToAlumnos: () -> Unit,
                    navigateToProfesores: () -> Unit,
                    navigateToPagos: () -> Unit,
                    navigateToHorarios: () -> Unit,
                    navigateBack: () -> Unit ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .background(colorResource(id = R.color.fondoOscuro))
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Menú",
                    modifier = Modifier.padding(18.dp),
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.tex1) // Color del texto
                )
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = Color.White, // Color del divisor
                    thickness = 2.dp
                )
                DrawerItem("Alumnos")
                DrawerItem("Profesores")
                DrawerItem("Horarios")
                DrawerItem("Pagos")
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Administrador",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.tex1)                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.fondoOscuro),
                        titleContentColor = colorResource(id = R.color.tex1),
                        navigationIconContentColor = colorResource(id = R.color.fondoClaro),
                        actionIconContentColor = colorResource(id = R.color.tex1)
                    ),

                    navigationIcon = {
                        IconButton(
                            onClick = { navigateBack() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = colorResource(id = R.color.tex1)
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
                                tint = colorResource(id = R.color.tex1)
                            )
                        }
                    }
                )
            },

        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(colorResource(id = R.color.fondoClaro))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when (userRole) {
                        "Admin" -> AdminPrincipal(
                                navigateToAlumnos,
                                navigateToProfesores,
                                navigateToHorarios,
                                navigateToPagos
                            )
//                        "Profesor" -> ProfesorPrincipal()
//                        "Alumno" -> AlumnoPrincipal()
                    }
                }
            }
        }
    }
}

@Composable
fun AdminPrincipal(
    navigateToAlumnos: () -> Unit,
    navigateToProfesores: () -> Unit,
    navigateToHorarios: () -> Unit,
    navigateToPagos: () -> Unit
) {
    // Usamos la clase MenuItem para almacenar cada ítem del menú
    val items = listOf(
        MenuItem(
            title = "Alumnos",
            description = "Resumen de alumnos registrados",
            colorResId = R.color.colorAlumno,
            onClick = navigateToAlumnos
        ),
        MenuItem(
            title = "Profesores",
            description = "Lista de profesores activos",
            colorResId = R.color.colorProfesor,
            onClick = navigateToProfesores
        ),
        MenuItem(
            title = "Horarios",
            description = "Horarios de clases",
            colorResId = R.color.colorHorario,
            onClick = navigateToHorarios
        ),
        MenuItem(
            title = "Pagos",
            description = "Pagos pendientes y realizados",
            colorResId = R.color.colorPago,
            onClick = navigateToPagos
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp) // Espaciado entre las tarjetas
    ) {
        items.forEach { item ->
            DashboardCard(
                title = item.title,
                description = item.description,
                color = colorResource(id = item.colorResId),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Cada tarjeta ocupará una fracción igual del espacio disponible
                    .clickable { item.onClick() } // Ejecutar la función onClick
            )
        }
    }


}


//@Composable
//fun ProfesorPrincipal() {
//    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//        DashboardCard("Horario", "Clases programadas", Color(0xFF0288D1))
//        DashboardCard("Notificaciones", "Notificaciones recientes", Color(0xFF7B1FA2))
//    }
//}
//
//@Composable
//fun AlumnoPrincipal() {
//    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//        DashboardCard("Clases", "Próximas clases programadas", Color(0xFF689F38))
//        DashboardCard("Pagos", "Pagos pendientes", Color(0xFFFBC02D))
//        DashboardCard("Tareas", "Tareas pendientes", Color(0xFF5D4037))
//    }
//}

@Composable
fun DashboardCard(title: String, description: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.tex1)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = description,
                fontSize = 20.sp,
                color = colorResource(R.color.tex1)
            )
        }
    }
}