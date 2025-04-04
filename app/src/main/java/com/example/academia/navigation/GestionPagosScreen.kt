package com.example.academia.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.academia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPagosScreen( navigateToInit: () -> Unit,
                          navigateBack: () -> Unit
                          ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .background(colorResource(id = R.color.colorPago))
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
                            text = "Administrador > Pagos",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.colorPago),
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

            bottomBar = {
                NavigationBar(
                    containerColor = colorResource(id = R.color.colorPago) // Colores de fondo
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
                                tint = colorResource(id = R.color.tex1)) // Color del icono
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            // Acción para el botón Home
                        },
                        icon = {
                            Icon(Icons.Filled.Home,
                                contentDescription = "Inicio",
                                tint = colorResource(id = R.color.tex1)) // Color del icono
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            // Acción para el botón de Ajustes
                        },
                        icon = {
                            Icon(Icons.Filled.Settings,
                                contentDescription = "Ajustes",
                                tint = colorResource(id = R.color.tex1)) // Color del icono
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
                // contenido principal de la pantalla
            }
        }
    }
}
