package com.example.notesproject.database

class NotesRepository(private val db : NoteDatabase)  {
    suspend fun insertNote(note : Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note : Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note : Note) = db.getNoteDao().updateNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun getAllCategoriesNames() = db.getCategoryDao().getAllCategoryNames()
    fun searchNote(query : String?) = db.getNoteDao().searchNote(query)
    suspend fun insertCategory(category: Category) = db.getCategoryDao().insertCategory(category)
}