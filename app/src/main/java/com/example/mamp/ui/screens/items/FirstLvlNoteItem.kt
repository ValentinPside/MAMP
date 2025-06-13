package com.example.mamp.ui.screens.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Icon


@Composable
fun FirstLvlNoteItem(note: FirstLvlNote, onClick: () -> Unit) {
    val background = if (note.isFinished == 0) Color.LightGray else Color.Transparent

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PictureAsPdf,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = note.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}