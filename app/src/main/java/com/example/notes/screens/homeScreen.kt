package com.example.notes.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.notes.CustomDrawer
import com.example.notes.NotesCard

@Composable
fun HomeScreen(navController: NavController) {
    CustomDrawer(
        content = { innerPadding ->
            NotesCard(innerPadding)
        })
}