package com.example.notes.screens

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddNotes : Screen("addNotes")
}