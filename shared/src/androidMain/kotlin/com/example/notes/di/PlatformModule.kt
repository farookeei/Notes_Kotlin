package com.example.notes.di

import com.example.notes.database.notes.NoteDatabase
import com.example.notes.database.notes.getDatabaseBuilder
import com.example.notes.database.notes.getRoomDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single { getRoomDatabase(getDatabaseBuilder(get())) }
}
