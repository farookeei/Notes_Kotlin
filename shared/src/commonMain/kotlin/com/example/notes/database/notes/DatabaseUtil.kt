package com.example.notes.database.notes

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getRoomDatabase(
    builder: RoomDatabase.Builder<NoteDatabase>
): NoteDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .build()
}
