package com.example.notesproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesproject.database.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Query("SELECT * FROM notes_table ORDER BY prio desc")
    fun getAllNotes() : LiveData<List<Note>>
    @Update
    suspend fun updateNote(note : Note)
    @Delete
    suspend fun  deleteNote(note : Note)
    @Query("SELECT * FROM notes_table WHERE noteTitle LIKE :query OR noteContent LIKE :query")
    fun searchNote(query: String?) : LiveData<List<Note>>

}