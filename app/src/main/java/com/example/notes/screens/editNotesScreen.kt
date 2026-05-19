package com.example.notes.screens

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.notes.CustomDrawer
import com.example.notes.database.notes.Note
import com.example.notes.viewmodel.NoteViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditNotesScreen(
    navController: NavController,
    id: Int?,
    viewModel: NoteViewModel = koinViewModel()
) {
    val note by viewModel.getNoteById(id).collectAsState(initial = null)
    val notesContentState = rememberTextFieldState()
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            title = { Text("Delete this note?") },
            text = { Text("Are you sure you want to delete this note?") },
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val currentNote = note
                        if (currentNote != null) {
                            scope.launch {
                                viewModel.deleteNote(note = currentNote)
                                showDialog = false
                                navController.popBackStack()
                            }
                        }
                    }
                ) {
                    Text("YES")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("NO")
                }
            },
        )
    }

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
                        scope.launch {
                            viewModel.editNote(id = id, content = notesContentState.text.toString())
                            navController.popBackStack()
                        }
                    }
                },
            ) {
                Icon(Icons.Filled.Check, "Add notes")
            }
        },
        appBarActions = {
            IconButton(
                onClick = {
                    showDialog = true
                }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
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