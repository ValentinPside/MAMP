package com.example.mamp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "firstLvlNoteTable"
)
data class FirstLvlNoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "target_date")
    val targetDate: LocalDate,
    @ColumnInfo(name = "file_address")
    val fileAddress: String,
    @ColumnInfo(name = "is_finished")
    val isFinished: Int

)
