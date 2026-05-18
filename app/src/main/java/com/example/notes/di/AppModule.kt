package com.example.notes.di

import com.example.notes.database.notes.NoteDatabase
import com.example.notes.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { NoteDatabase.getDatabase(get()) }

    single { get<NoteDatabase>().noteDao() }

    viewModel { NoteViewModel(get()) }
}
