package com.example.notes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.notes.CustomDrawer

@Composable
fun AddNotesScreen(navController: NavController, id: String?) {
    CustomDrawer(content = { innerPadding ->
        Column() {
            Text("Add Notes")
        }
    })


}