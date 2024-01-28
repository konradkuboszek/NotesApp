package com.example.notesproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesproject.database.Category
import com.example.notesproject.database.Note
import com.example.notesproject.database.NotesRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val notesRepository: NotesRepository) : AndroidViewModel(app) {
    fun addNote(note:Note) =
        viewModelScope.launch {
            notesRepository.insertNote(note)
        }
    fun deleteNote(note:Note) =
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    fun updateNote(note:Note) =
        viewModelScope.launch {
            notesRepository.updateNote(note)
        }
    fun getAllNotes() = notesRepository.getAllNotes()

    fun searchNote(query : String?) =
        notesRepository.searchNote(query)
    fun insertCategory(category: Category) =
        viewModelScope.launch {
            notesRepository.insertCategory(category)
        }
    fun getCategoriesNames()=
        viewModelScope.launch {
            notesRepository.getAllCategoriesNames()
        }

}