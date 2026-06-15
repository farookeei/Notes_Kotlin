package com.example.notes.repository

import com.example.notes.database.notes.Note
import com.example.notes.database.notes.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface INoteRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNoteById(id: Int?): Flow<Note?>
    suspend fun addNote(content: String)
    suspend fun editNote(id: Int, content: String)
    suspend fun deleteNote(note: Note)
}

class NoteRepository(private val dao: NoteDao) : INoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun getNoteById(id: Int?): Flow<Note?> {
        return getNotes().map { notes -> notes.find { it.id == id } }
    }

    override suspend fun addNote(content: String) {
        dao.insertNote(Note(content = content))
    }

    override suspend fun editNote(id: Int, content: String) {
        dao.editNote(Note(id = id, content = content))
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

}