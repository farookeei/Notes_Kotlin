package com.example.notes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.notes.CustomDrawer
import com.example.notes.viewmodel.NoteViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddNotesScreen(navController: NavController, viewModel: NoteViewModel = koinViewModel()) {
    val notesContent = rememberTextFieldState()
    val scope = rememberCoroutineScope()


    CustomDrawer(
        navController = navController,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        viewModel.addNote(notesContent.text.toString())
                        navController.popBackStack()
                    }
                },
            ) {
                Icon(Icons.Filled.Check, "Add notes")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    state = notesContent,
                    placeholder = { Text("Write notes ") }
                )
            }
        })


}