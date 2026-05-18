package com.example.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.database.notes.Note
import com.example.notes.database.notes.NoteDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class NoteViewModel(private val dao: NoteDao) : ViewModel() {
    // By converting the Flow to a StateFlow, the UI can observe 'notes'
    // and will automatically update whenever the database changes.
    val getNotes: StateFlow<List<Note>> = dao.getNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getNoteById(id: Int?): Flow<Note?> {
        return getNotes.map { notes -> notes.find { it.id == id } }
    }

    fun addNote(content: String) {
        viewModelScope.launch {
            dao.insertNote(
                Note(
                    content = content
                )
            )
        }
    }

    fun editNote(id: Int, content: String) {
        viewModelScope.launch {
            dao.editNote(
                Note(
                    id = id,
                    content = content
                )
            )
        }
    }


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.deleteNote(note)
        }
    }
}
