package com.example.notesproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesproject.database.Note

@Database(entities = [Note::class, Category::class], version = 2)

abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao() : NoteDao
    abstract fun getCategoryDao() : CategoryDao

    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase? = null
        fun getDatabase(context : Context) : NoteDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }

    }
}