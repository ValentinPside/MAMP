package com.example.mamp.domain.repository

import com.example.mamp.domain.models.FirstLvlNote

interface FirstLvlNoteRepository {

    suspend fun getFirstLvlList(): List<FirstLvlNote>

}