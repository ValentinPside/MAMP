package com.example.mamp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version = 1, entities = [FirstLvlNoteEntity::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class MainDb : RoomDatabase() {
    abstract fun dao(): Dao
}