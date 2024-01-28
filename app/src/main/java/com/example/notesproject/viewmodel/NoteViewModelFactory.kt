package com.example.notesproject.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesproject.database.NotesRepository

class NoteViewModelFactory(val app: Application, private val notesRepository: NotesRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create (modelClass: Class<T>): T{
        return NoteViewModel(app,notesRepository) as T
    }
}