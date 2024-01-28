package com.example.notesproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)
    @Query("SELECT * FROM categories_table")
    fun getAllCategories() : LiveData<List<Category>>
    @Query("SELECT categoryName FROM categories_table")
    fun getAllCategoryNames(): LiveData<List<String>>
}