package com.example.notes.di

import com.example.notes.database.notes.NoteDatabase
import com.example.notes.repository.INoteRepository
import com.example.notes.repository.NoteRepository
import com.example.notes.viewmodel.NoteViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    single { get<NoteDatabase>().noteDao() }
    single<INoteRepository> { NoteRepository(get()) }
    single { NoteViewModel(get()) }
}

val appModule = listOf(platformModule, sharedModule)
