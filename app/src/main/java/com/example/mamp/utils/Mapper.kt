package com.example.mamp.utils

import com.example.mamp.data.db.entities.FirstLvlNoteEntity
import com.example.mamp.data.db.entities.SecondLvlNoteEntity
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.models.SecondLvlNote

fun FirstLvlNoteEntity.asFirstLvlNote() = FirstLvlNote(
    id = this.id,
    name = this.name,
    targetDate = this.targetDate,
    fileAddress = this.fileAddress,
    isFinished = this.isFinished
)

fun FirstLvlNote.asFirstLvlNoteEntity() = FirstLvlNoteEntity(
    id = this.id,
    name = this.name,
    targetDate = this.targetDate,
    fileAddress = this.fileAddress,
    isFinished = this.isFinished
)

fun SecondLvlNoteEntity.asSecondLvlNote() = SecondLvlNote(
    id = this.id,
    parentId = this.parentId,
    name = this.name,
    fileAddress = this.fileAddress
)

fun SecondLvlNote.asSecondLvlNoteEntity() = SecondLvlNoteEntity(
    id = this.id,
    parentId = this.parentId,
    name = this.name,
    fileAddress = this.fileAddress
)
fun List<SecondLvlNoteEntity>.asSecondLvlNoteList() = this.map { it.asSecondLvlNote() }

fun List<FirstLvlNoteEntity>.asFirstLvlNoteList() = this.map { it.asFirstLvlNote() }