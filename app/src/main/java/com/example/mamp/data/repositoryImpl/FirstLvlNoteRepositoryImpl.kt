package com.example.mamp.data.repositoryImpl

import com.example.mamp.data.db.FirstLvlNoteEntity
import javax.inject.Inject

class FirstLvlNoteRepository @Inject constructor(
    private val dao: FirstLvlNoteDao
) {
    suspend fun insert(note: FirstLvlNoteEntity) = dao.insert(note)
    suspend fun getAll() = dao.getAll()
    suspend fun searchByName(query: String) = dao.searchByName(query)
    suspend fun delete(note: FirstLvlNoteEntity) = dao.delete(note)
}