package com.example.mamp.domain.repository

import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.models.SecondLvlNote

interface SecondLvlNoteRepository {
    suspend fun getFirstLvlNoteById(noteId: Int): FirstLvlNote
    suspend fun getSecondLvlNotesByParentId(noteId: Int): List<SecondLvlNote>
}