package com.example.notes.database.notes

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun getDatabaseBuilder(): RoomDatabase.Builder<NoteDatabase> {
    val dbFilePath = NSHomeDirectory() + "/note_database.db"
    return Room.databaseBuilder<NoteDatabase>(
        name = dbFilePath,
        factory = { NoteDatabase::class.instantiateImpl() }
    )
}
