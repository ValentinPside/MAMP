package com.example.mamp.data.repositoryImpl

import com.example.mamp.data.db.MainDb
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.models.SecondLvlNote
import com.example.mamp.domain.repository.SecondLvlNoteRepository
import com.example.mamp.utils.asFirstLvlNote
import com.example.mamp.utils.asFirstLvlNoteEntity
import com.example.mamp.utils.asSecondLvlNoteEntity
import com.example.mamp.utils.asSecondLvlNoteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecondLvlNoteRepositoryImpl @Inject constructor(private val db: MainDb) :
    SecondLvlNoteRepository {

    override suspend fun getFirstLvlNoteById(noteId: Int): FirstLvlNote {
        return withContext(Dispatchers.IO) {
            val note = db.dao().getFirstLvlNoteById(noteId)
            note.asFirstLvlNote()
        }
    }

    override suspend fun getSecondLvlNotesByParentId(noteId: Int): List<SecondLvlNote> {
        return withContext(Dispatchers.IO) {
            val list = db.dao().getAllSecondLvlNote(noteId)
            list.asSecondLvlNoteList()
        }
    }

    override suspend fun insertSecondLvlNote(note: SecondLvlNote) {
        val noteEntity = note.asSecondLvlNoteEntity()
        db.dao().insertSecondLvlNote(noteEntity)
    }

    override suspend fun updateFirstLvlNote(note: FirstLvlNote) {
        val noteEntity = note.asFirstLvlNoteEntity()
        db.dao().updateFirstLvlNote(noteEntity)
    }

}