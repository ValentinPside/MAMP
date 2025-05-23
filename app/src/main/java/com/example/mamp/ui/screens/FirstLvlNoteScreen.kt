package com.example.mamp.ui.screens

@Composable
fun FirstLvlNoteScreen(viewModel: FirstLvlNoteViewModel = hiltViewModel()) {
    val notes by viewModel.notes.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Поиск
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.search(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Поиск по имени") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Список заметок
        LazyColumn {
            items(notes) { note ->
                FirstLvlNoteItem(note = note, onDelete = {
                    viewModel.deleteNote(note)
                })
            }
        }
    }

    // Загружаем все заметки при запуске
    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }
}