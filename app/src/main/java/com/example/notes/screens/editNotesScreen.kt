package com.example.notes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.notes.CustomDrawer
import com.example.notes.database.notes.Note
import com.example.notes.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditNotesScreen(
    navController: NavController,
    id: Int?,
    viewModel: NoteViewModel = koinViewModel()
) {
    val note by viewModel.getNoteById(id).collectAsState(initial = null)
    val notesContentState = rememberTextFieldState()

    LaunchedEffect(note) {
        val currentNote = note
        if (currentNote != null && notesContentState.text.isEmpty()) {
            notesContentState.setTextAndPlaceCursorAtEnd(currentNote.content)
        }
    }

    CustomDrawer(
        navController = navController,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (id != null) {
                        viewModel.editNote(id = id, content = notesContentState.text.toString())
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
                    state = notesContentState
                )
            }
        }
    )

}