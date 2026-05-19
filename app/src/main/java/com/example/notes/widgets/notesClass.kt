package com.example.notes.widgets

import android.app.AlertDialog
import android.app.Dialog
import android.util.Log
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun NotesCard(title: String, onClick: () -> Unit, onDeleteConfirm: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
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
                        scope.launch {
                            onDeleteConfirm()
                            showDialog = false
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
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(50.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    showDialog = true

                    Log.d("NotesCard", "Long press triggered")
                }
            )
    ) {
        Text(
            text = title.take(10),
            modifier = Modifier.padding(16.dp)
        )
    }
}
