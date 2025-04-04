package com.example.academia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable

fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Inicio) {
        composable<Inicio> {
            InicioScreen { navController.navigate(Login) }
        }
        composable<Login> {
            LoginScreen {navController.navigate(Principal)}
        }
        composable<Principal> {
            PrincipalScreen("Admin",
                {navController.navigate(Inicio)},
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                {navController.popBackStack()}
            )
        }
        composable<GestionAlumnos> {
            GestionAlumnosScreen(
                {navController.navigate(Inicio)},
                { navController.popBackStack() })
        }
        composable<GestionProfesores> {
            GestionProfesoresScreen(
                {navController.navigate(Inicio)},
                { navController.popBackStack() })
        }
        composable<GestionPagos> {
            GestionPagosScreen(
                {navController.navigate(Inicio)},
                { navController.popBackStack() })
        }
        composable<GestionHorarios> {
            GestionHorariosScreen(
                {navController.navigate(Inicio)},
                { navController.popBackStack() })
        }

    }
}