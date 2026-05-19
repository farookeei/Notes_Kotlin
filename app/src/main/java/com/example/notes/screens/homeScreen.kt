package com.example.notes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notes.CustomDrawer
import com.example.notes.viewmodel.NoteViewModel
import com.example.notes.widgets.NotesCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: NoteViewModel = koinViewModel()) {
    val notes by viewModel.getNotes.collectAsState()
    CustomDrawer(navController = navController, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate(route = Screen.AddNotes.route)
            },
        ) {
            Icon(Icons.Filled.Add, "Add notes")
        }
    }, content = { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            if (notes.isEmpty()) {
                item {
                    Text(
                        text = "No notes found",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
            items(notes) { note ->
                NotesCard(
                    title = note.content,
                    onClick = {
                        navController.navigate(route = Screen.EditNotes.route + "/${note.id}")
                    }
                )
            }
//            items(notes) { note ->
//                NotesCard(
//                    title = note.content,
//                    onClick = {
//                        navController.navigate(route = Screen.EditNotes.route + "/${note.id}")
//                    }
//                )
//            }
        }
    })
}