package com.example.notes.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.notes.CustomDrawer
import com.example.notes.widgets.NotesCard

@Composable
fun HomeScreen(navController: NavController) {
    CustomDrawer(
        navController = navController,
        content = { innerPadding ->
            NotesCard(innerPadding)
        })
}