package com.example.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.database.notes.Note
import com.example.notes.repository.INoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow


class NoteViewModel(private val repository: INoteRepository) :
    ViewModel() {
    // By converting the Flow to a StateFlow, the UI can observe 'notes'
    // and will automatically update whenever the database changes.
    val getNotes: StateFlow<List<Note>> =
        repository.getNotes().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun getNoteById(id: Int?): Flow<Note?> {
        return repository.getNoteById(id)
    }

    suspend fun addNote(content: String) {
        repository.addNote(content)

    }

    suspend fun editNote(id: Int, content: String) {
        repository.editNote(id, content)
    }

    suspend fun deleteNote(note: Note) {
        repository.deleteNote(note)

    }
}