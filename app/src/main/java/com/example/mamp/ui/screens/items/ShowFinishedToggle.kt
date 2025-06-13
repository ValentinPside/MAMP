package com.example.mamp.ui.screens.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowFinishedToggle(
    isChecked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onCheckedChange() }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange() }
        )
        Text("Только завершённые", modifier = Modifier.padding(start = 4.dp))
    }
}