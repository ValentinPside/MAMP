package com.example.mamp.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mamp.app.App
import com.example.mamp.ui.viewmodels.FirstLvlNoteViewModel
import com.example.mamp.utils.Factory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mamp.R
import com.example.mamp.ui.screens.items.DatePickerButton
import com.example.mamp.ui.screens.items.FilterCircle
import com.example.mamp.ui.screens.items.FirstLvlNoteItem
import com.example.mamp.ui.screens.items.ShowFinishedToggle
import com.example.mamp.utils.NoteColorFilter
import java.time.LocalDate

@Composable
fun FirstLvlNoteScreen(
    navController: NavHostController,
    viewModel: FirstLvlNoteViewModel = viewModel(factory = Factory {
        App.appComponent.firstLvlNoteComponent().viewModel()
    })
) {
    val context = LocalContext.current
    val firstLvlListState by viewModel.observeUi().collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    val pickPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                selectedPdfUri = it
                showAddDialog = true
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getFirstLvlList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.orion_lable),
                contentDescription = "Логотип",
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            maxLines = 1,
            onValueChange = {
                searchQuery = it
                viewModel.setSearchQuery(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Поиск по адресу") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButton(
                onClick = { pickPdfLauncher.launch(arrayOf("application/pdf")) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить файл")
            }

            Column(horizontalAlignment = Alignment.End) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val selected = firstLvlListState.selectedColor

                    FilterCircle(
                        color = Color.Gray,
                        selected = selected == null,
                        onClick = { viewModel.setFilter(null) },
                        icon = Icons.Default.Clear
                    )

                    FilterCircle(
                        color = Color.Red,
                        selected = selected == NoteColorFilter.RED,
                        onClick = { viewModel.setFilter(if (selected == NoteColorFilter.RED) null else NoteColorFilter.RED) }
                    )
                    FilterCircle(
                        color = Color.Yellow,
                        selected = selected == NoteColorFilter.YELLOW,
                        onClick = { viewModel.setFilter(if (selected == NoteColorFilter.YELLOW) null else NoteColorFilter.YELLOW) }
                    )
                    FilterCircle(
                        color = Color.Green,
                        selected = selected == NoteColorFilter.GREEN,
                        onClick = { viewModel.setFilter(if (selected == NoteColorFilter.GREEN) null else NoteColorFilter.GREEN) }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                ShowFinishedToggle(
                    isChecked = firstLvlListState.showOnlyFinished,
                    onCheckedChange = { viewModel.toggleShowOnlyFinished() }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            firstLvlListState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            firstLvlListState.error != null -> {
                Toast.makeText(
                    context,
                    context.getString(firstLvlListState.error!!),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
                firstLvlListState.firstLvlList?.let { notes ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(notes) { note ->
                            FirstLvlNoteItem(note = note) {
                                navController.navigate("second_lvl_note_screen/${note.id}")
                            }
                        }
                    }
                }
            }
        }

        if (showAddDialog && selectedPdfUri != null) {
            var address by remember { mutableStateOf("") }
            var date by remember { mutableStateOf(LocalDate.now()) }

            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Добавить файл") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Адрес") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        DatePickerButton(date = date) { selected ->
                            date = selected
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        selectedPdfUri?.let { uri ->
                            viewModel.addPdfFile(uri, address, date)
                        }
                        showAddDialog = false
                        selectedPdfUri = null
                    }) {
                        Text("Сохранить")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAddDialog = false
                        selectedPdfUri = null
                    }) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}