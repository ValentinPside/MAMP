package com.example.mamp.ui.screens.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mamp.domain.models.FirstLvlNote
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun FirstLvlNoteItem(note: FirstLvlNote) {
    val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), note.targetDate)
    val bgColor = when {
        daysLeft <= 14 -> Color.Red
        daysLeft <= 28 -> Color.Yellow
        else -> Color.Green
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f) // квадрат, удобно для 3 в строке
            .fillMaxWidth()
            .background(bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = note.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Black
            )
        }
    }
}