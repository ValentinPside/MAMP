package com.example.mamp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mamp.data.db.entities.FirstLvlNoteEntity
import com.example.mamp.data.db.entities.SecondLvlNoteEntity

@Database(version = 1, entities = [FirstLvlNoteEntity::class, SecondLvlNoteEntity::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class MainDb : RoomDatabase() {
    abstract fun dao(): Dao
}