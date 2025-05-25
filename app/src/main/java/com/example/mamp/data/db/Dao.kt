package com.example.mamp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: FirstLvlNoteEntity)

    @Query("SELECT * FROM firstLvlNoteTable ORDER BY id DESC")
    suspend fun getAll(): List<FirstLvlNoteEntity>

    @Query("SELECT * FROM firstLvlNoteTable WHERE name = :id")
    suspend fun getFirstLvlNoteById(id: Int): FirstLvlNoteEntity

    @Delete
    suspend fun delete(note: FirstLvlNoteEntity)
}