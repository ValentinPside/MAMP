package com.example.mamp.utils

import androidx.compose.ui.graphics.Color
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