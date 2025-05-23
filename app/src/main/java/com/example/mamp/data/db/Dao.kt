package com.example.mamp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: FirstLvlNoteEntity): Int

    @Query("SELECT * FROM firstLvlNoteTable ORDER BY id DESC")
    suspend fun getAll(): List<FirstLvlNoteEntity>

    @Query("SELECT * FROM firstLvlNoteTable WHERE name LIKE '%' || :query || '%'")
    suspend fun searchByName(query: String): List<FirstLvlNoteEntity>

    @Delete
    suspend fun delete(note: FirstLvlNoteEntity)
}