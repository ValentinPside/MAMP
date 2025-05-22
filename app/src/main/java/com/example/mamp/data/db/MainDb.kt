package com.example.mamp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [FirstLvlNoteEntity::class], exportSchema = false)
abstract class MainDb : RoomDatabase() {
    abstract fun dao(): Dao
}