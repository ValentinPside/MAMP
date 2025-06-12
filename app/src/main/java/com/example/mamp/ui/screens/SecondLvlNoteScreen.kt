package com.example.mamp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mamp.app.App
import com.example.mamp.ui.viewmodels.SecondLvlNoteViewModel
import com.example.mamp.utils.Factory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mamp.ui.screens.items.DatePickerButton
import java.time.LocalDate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment

@Composable
fun SecondLvlNoteScreen(
    navController: NavHostController,
    noteId: Int,
    viewModel: SecondLvlNoteViewModel = viewModel(factory = Factory {
        App.appComponent.secondLvlNoteComponent().viewModel()
    })
) {
    val state by viewModel.observeUi().collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        viewModel.loadNotesForFirstLvl(noteId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        state.parentNote?.let { note ->
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Документ: ${note.name}", style = MaterialTheme.typography.titleLarge)
                    Text("Дата: ${note.targetDate}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Text("Вложенные документы:", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        LazyColumn {
            items(state.childNotes) { child ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(child.name, style = MaterialTheme.typography.titleMedium)
                        Text(child.targetDate.toString(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить вложенный документ")
        }

        if (showDialog) {
            var name by remember { mutableStateOf("") }
            var date by remember { mutableStateOf(LocalDate.now()) }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Добавить вложенный документ") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Название") }
                        )
                        Spacer(Modifier.height(8.dp))
                        DatePickerButton(date = date) { date = it }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        //viewModel.addSecondLvlNote(noteId, name, date)
                        showDialog = false
                    }) {
                        Text("Сохранить")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}