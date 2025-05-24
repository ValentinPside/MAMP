package com.example.mamp.data.repositoryImpl

import com.example.mamp.data.db.MainDb
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.repository.FirstLvlNoteRepository
import com.example.mamp.utils.asFirstLvlNoteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirstLvlNoteRepositoryImpl @Inject constructor(private val db: MainDb):
    FirstLvlNoteRepository {
    override suspend fun getFirstLvlList(): List<FirstLvlNote> {
        return withContext(Dispatchers.IO) {
            val list = db.dao().getAll()
            list.asFirstLvlNoteList()
        }
    }

}