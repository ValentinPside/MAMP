package com.example.mamp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mamp.data.db.entities.FirstLvlNoteEntity
import com.example.mamp.data.db.entities.SecondLvlNoteEntity

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFirstLvlNote(note: FirstLvlNoteEntity)

    @Query("SELECT * FROM firstLvlNoteTable ORDER BY id DESC")
    suspend fun getAllFirstLvlNote(): List<FirstLvlNoteEntity>

    @Query("SELECT * FROM firstLvlNoteTable WHERE id = :id")
    suspend fun getFirstLvlNoteById(id: Int): FirstLvlNoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSecondLvlNote(note: SecondLvlNoteEntity)

    @Query("SELECT * FROM secondLvlNoteTable WHERE parent_id = :id ORDER BY id DESC")
    suspend fun getAllSecondLvlNote(id: Int): List<SecondLvlNoteEntity>

    @Delete
    suspend fun deleteFirstLvlNote(note: FirstLvlNoteEntity)

    @Delete
    suspend fun deleteSecondLvlNote(note: SecondLvlNoteEntity)
}