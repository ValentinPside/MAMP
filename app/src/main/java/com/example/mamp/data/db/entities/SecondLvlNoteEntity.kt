package com.example.mamp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "secondLvlNoteTable"
)
data class SecondLvlNoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "parent_id")
    val parentId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "file_address")
    val fileAddress: String
)
