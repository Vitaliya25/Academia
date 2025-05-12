package com.example.academia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Inicio) {
        composable<Inicio> {
            InicioScreen { navController.navigate(Login) }
        }
        composable<Login> {
            LoginScreen ({ navController.navigate(Principal) })
        }
        composable<Principal> {
            PrincipalScreen(
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
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                { navController.popBackStack() },
                { alumnoId -> navController.navigate("alumnoDetalle/$alumnoId") }
            )
        }
        composable<GestionProfesores> {
            GestionProfesoresScreen(
                {navController.navigate(Inicio)},
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                { navController.popBackStack() },
                { profesorId -> navController.navigate("profesorDetalle/$profesorId") }
            )
        }
        composable<GestionPagos> {
            GestionPagosScreen(
                {navController.navigate(Inicio)},
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                { navController.popBackStack() })
        }
        composable<GestionHorarios> {
            GestionHorariosScreen(
                {navController.navigate(Inicio)},
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                {navController.popBackStack()},
                { claseId -> navController.navigate("claseDetalle/$claseId") }
            )
        }

        composable(
            route = "alumnoDetalle/{alumnoId}",
            arguments = listOf(navArgument("alumnoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val alumnoId = backStackEntry.arguments?.getLong("alumnoId")
            DetallesAlumnoScreen(alumnoId,
                {navController.navigate(Inicio)},
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                { navController.popBackStack() }
                )
        }

        composable(
            route = "profesorDetalle/{profesorId}",
            arguments = listOf(navArgument("profesorId") { type = NavType.LongType })
        ) { backStackEntry ->
            val profesorId = backStackEntry.arguments?.getLong("profesorId")
            DetallesProfesorScreen(profesorId,
                {navController.navigate(Inicio)},
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                { navController.popBackStack() }
            )
        }


        composable(
            route = "claseDetalle/{claseId}",
            arguments = listOf(navArgument("claseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getLong("claseId")
            DetallesClaseScreen(claseId,
                {navController.navigate(Inicio)},
                {navController.navigate(GestionAlumnos)},
                {navController.navigate(GestionProfesores)},
                {navController.navigate(GestionPagos)},
                {navController.navigate(GestionHorarios)},
                { navController.popBackStack() }
            )
        }

    }
}