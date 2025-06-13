package com.example.mamp.ui.screens.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mamp.domain.models.FirstLvlNote
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.mamp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Composable
fun FirstLvlNoteItem(note: FirstLvlNote, onClick: () -> Unit) {
    val backgroundColor = getColorByDate(note.targetDate, note
        .isFinished)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.doc_icon),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )

        Text(
            text = note.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = note.targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.DarkGray
        )
    }
}

private fun getColorByDate(date: LocalDate, isFinished: Int): Color {
    val today = LocalDate.now()
    val daysBetween = ChronoUnit.DAYS.between(today, date)

    return when {
        isFinished == 0 -> Color.Gray
        daysBetween <= 14 -> Color.Red
        daysBetween in 15..18 -> Color.Yellow
        else -> Color.Green
    }
}
