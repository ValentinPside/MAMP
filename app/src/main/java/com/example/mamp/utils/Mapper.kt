package com.example.mamp.utils

import androidx.compose.ui.graphics.Color
import com.example.mamp.data.db.FirstLvlNoteEntity
import com.example.mamp.domain.models.FirstLvlNote
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun getBackgroundColor(date: LocalDate): Color {
    val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), date)

    return when {
        daysLeft <= 14 -> Color.Red
        daysLeft <= 28 -> Color.Yellow
        else -> Color.Green
    }
}

fun FirstLvlNoteEntity.asFirstLvlNote() = FirstLvlNote(
    id = this.id,
    name = this.name,
    targetDate = this.targetDate,
    fileAddress = this.fileAddress
)

fun FirstLvlNote.asFirstLvlNoteEntity() = FirstLvlNoteEntity(
    id = this.id,
    name = this.name,
    targetDate = this.targetDate,
    fileAddress = this.fileAddress
)

fun List<FirstLvlNoteEntity>.asFirstLvlNoteList() = this.map { it.asFirstLvlNote() }