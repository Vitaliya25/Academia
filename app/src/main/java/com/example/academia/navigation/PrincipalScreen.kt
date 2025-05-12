package com.example.academia.navigation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.academia.R
import com.example.academia.model.MenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(
                    navigateToInit: () -> Unit,
                    navigateToAlumnos: () -> Unit,
                    navigateToProfesores: () -> Unit,
                    navigateToPagos: () -> Unit,
                    navigateToHorarios: () -> Unit,
                    navigateBack: () -> Unit ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
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
                            color = colorResource(id = R.color.textoBlanco)                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.pantallaPrincipal),
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
                                tint = colorResource(id = R.color.textoBlanco)
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
                ) { AdminPrincipal(
                                navigateToAlumnos,
                                navigateToProfesores,
                                navigateToHorarios,
                                navigateToPagos
                            )
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
            description = "",
            colorResId = R.color.colorAlumno,
            imageResId = R.drawable.alumnos3, // Imagen de fondo
            onClick = navigateToAlumnos
        ),
        MenuItem(
            title = "Profesores",
            description = "",
            colorResId = R.color.colorProfesor,
            imageResId = R.drawable.profesores, // Imagen de fondo
            onClick = navigateToProfesores
        ),
        MenuItem(
            title = "Horarios",
            description = "",
            colorResId = R.color.colorHorario,
            imageResId = R.drawable.horarios2, // Imagen de fondo
            onClick = navigateToHorarios
        ),
        MenuItem(
            title = "Pagos",
            description = "",
            colorResId = R.color.colorPago,
            imageResId = R.drawable.images, // Imagen de fondo
            onClick = navigateToPagos
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items.forEach { item ->
            DashboardCard(
                title = item.title,
                description = item.description,
                color = colorResource(id = item.colorResId),
                imageResId = item.imageResId,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable { item.onClick() }
            )
        }
    }
}




@Composable
fun DashboardCard(title: String, description: String, color: Color, imageResId: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
//            .border(3.dp, color, RoundedCornerShape(16.dp))             .padding(bottom = 8.dp)

    ) {
        // Image de fondo
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para que coincidan con la tarjeta
//                .background(color = color.copy(alpha = 0.01f))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.4f)) // Fondo que oscurece o aclara la imagen
        )
        // Contenido de la tarjeta
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)  // Un poco de margen dentro de la tarjeta
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = title,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray

                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = description,
                    fontSize = 25.sp,
                    color = colorResource(R.color.textoBlanco)
                )
            }
        }
    }
}
