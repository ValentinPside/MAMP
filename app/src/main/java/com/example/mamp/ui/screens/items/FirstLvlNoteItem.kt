package com.example.mamp.ui.screens.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mamp.R
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

    val visibleState = remember { MutableTransitionState(false).apply { targetState = true } }


    AnimatedVisibility(visibleState = visibleState) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgColor),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.doc_icon),
                    contentDescription = "Документ",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(0.6f),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = note.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = note.targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black
                )
            }
        }
    }
}