package com.example.mamp.ui.screens.items

@Composable
fun FirstLvlNoteItem(
    note: FirstLvlNoteEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        backgroundColor = Color(note.color.toColorInt()),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = note.name, style = MaterialTheme.typography.body1)
                Text(text = note.fileAddress, style = MaterialTheme.typography.caption)
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}