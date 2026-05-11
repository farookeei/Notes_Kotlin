package com.example.notes.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotesCard(innerPadding: PaddingValues) {
    Card(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Text("I was going to ...")
    }
}
