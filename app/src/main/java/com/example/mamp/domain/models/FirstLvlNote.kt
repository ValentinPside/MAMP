package com.example.mamp.domain.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class FirstLvlNote(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "file_address")
    val fileAddress: String
)
