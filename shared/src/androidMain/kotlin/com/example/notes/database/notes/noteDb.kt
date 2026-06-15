package com.example.notes.database.notes

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<NoteDatabase> {
    val dbFile = context.getDatabasePath("note_database.db")
    return Room.databaseBuilder<NoteDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}
