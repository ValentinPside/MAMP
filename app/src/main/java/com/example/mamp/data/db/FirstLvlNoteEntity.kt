package com.example.mamp.data.db

import androidx.room.Entity

@Entity(
    tableName = "firstLvlNoteTable"
)
data class FirstLvlNoteEntity(
    val id: Int,
    val name: String,
    val color: String,
    val fileAddress: String
)
