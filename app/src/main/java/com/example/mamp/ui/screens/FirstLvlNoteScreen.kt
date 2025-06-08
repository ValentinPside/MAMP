package com.example.mamp.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.mamp.ui.screens.items.FirstLvlNoteItem

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

    val contentResolver = context.contentResolver

    val pickPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                // Пример: сохранить в базу
                viewModel.addPdfFile(uri)
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
                .height(80.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.orion_lable),
                contentDescription = "Логотип",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Строка поиска
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                //viewModel.search(searchQuery) // <- реализовать поиск
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Поиск по адресу") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        FloatingActionButton(
            onClick = { pickPdfLauncher.launch(arrayOf("application/pdf")) },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить файл")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Прогресс / Ошибка / Список
        when {
            firstLvlListState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            firstLvlListState.error != null -> {
                Toast.makeText(context, context.getString(firstLvlListState.error!!), Toast.LENGTH_SHORT).show()
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
                            FirstLvlNoteItem(note)
                        }
                    }
                }
            }
        }
    }
}