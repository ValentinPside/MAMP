package com.example.mamp.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mamp.app.App
import com.example.mamp.ui.viewmodels.SecondLvlNoteViewModel
import com.example.mamp.utils.Factory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextOverflow
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.models.SecondLvlNote
import androidx.compose.material3.Scaffold
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.mamp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondLvlNoteScreen(
    navController: NavHostController,
    noteId: Int,
    viewModel: SecondLvlNoteViewModel = viewModel(factory = Factory {
        App.appComponent.secondLvlNoteComponent().viewModel()
    })
) {
    val context = LocalContext.current
    val state by viewModel.observeUi().collectAsStateWithLifecycle()

    var showAddDialog by remember { mutableStateOf(false) }
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }

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

    LaunchedEffect(noteId) {
        viewModel.loadNotesForFirstLvl(noteId)
    }

    fun openFile(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Нет приложения для открытия PDF", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.parentNote?.name ?: "Документ") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        pickPdfLauncher.launch(arrayOf("application/pdf"))
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить вложение")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            val documents = buildList {
                state.parentNote?.let { add(it.toSecondLvlNote()) }
                addAll(state.childNotes)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(documents) { doc ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { openFile(Uri.parse(doc.fileAddress)) }
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
                            text = doc.name,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.toggleFinishedState() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    if (state.parentNote?.isFinished == 1) "Отметить как выполненное" else "Отметить как невыполненное"
                )
            }
        }
    }
    if (showAddDialog && selectedPdfUri != null) {
        var fileName by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                selectedPdfUri = null
            },
            title = { Text("Добавить вложение") },
            text = {
                OutlinedTextField(
                    value = fileName,
                    onValueChange = { fileName = it },
                    label = { Text("Введите имя файла") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val uri = selectedPdfUri
                        if (uri != null && fileName.isNotBlank()) {
                            viewModel.addSecondLvlNote(
                                parentId = noteId,
                                name = fileName,
                                uri = uri
                            )
                            showAddDialog = false
                            selectedPdfUri = null
                        }
                    }
                ) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddDialog = false
                        selectedPdfUri = null
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}

private fun FirstLvlNote.toSecondLvlNote(): SecondLvlNote {
    return SecondLvlNote(
        id = this.id,
        parentId = -1,
        name = this.name,
        fileAddress = this.fileAddress
    )
}
