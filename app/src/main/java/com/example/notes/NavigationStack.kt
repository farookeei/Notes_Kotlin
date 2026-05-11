package com.example.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.screens.AddNotesScreen
import com.example.notes.screens.HomeScreen
import com.example.notes.screens.Screen

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.AddNotes.route + "?id={id}", arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.StringType
                    nullable = true
                })
        ) {
            AddNotesScreen(navController, id = it.arguments?.getString("id"))
        }
    }
}