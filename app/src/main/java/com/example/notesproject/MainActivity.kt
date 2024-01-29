package com.example.notesproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notesproject.database.Category
import com.example.notesproject.database.NoteDatabase
import com.example.notesproject.database.NotesRepository
import com.example.notesproject.viewmodel.NoteViewModel
import com.example.notesproject.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel(){
        val noteDatabase = NoteDatabase.getDatabase(this)
        val notesRepository = NotesRepository(noteDatabase)
        val viewModelProviderFactory = NoteViewModelFactory(application, notesRepository)
        noteViewModel = ViewModelProvider(this,viewModelProviderFactory)[NoteViewModel::class.java]
    }

}