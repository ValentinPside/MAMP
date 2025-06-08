package com.example.mamp.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamp.R
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.repository.FirstLvlNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class FirstLvlNoteViewModel @Inject constructor(
    private val repository: FirstLvlNoteRepository
) : ViewModel() {

    private val state = MutableStateFlow(ViewStateFirstLvlNote())
    fun observeUi() = state.asStateFlow()

    fun getFirstLvlList() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            try {
                val list = repository.getFirstLvlList()
                state.update { it.copy(firstLvlList = list, error = null) }
            } catch (e: Exception) {
                state.update { it.copy(error = R.string.error_message) }
            } finally {
                state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun addPdfFile(uri: Uri) {
        viewModelScope.launch {
            val fileName = uri.lastPathSegment?.substringAfterLast("/") ?: "PDF"

            val newNote = FirstLvlNote(
                id = 0,
                name = fileName,
                fileAddress = uri.toString(),
                targetDate = LocalDate.now().plusDays(30) // по умолчанию через 30 дней
            )

            repository.insertNote(newNote)
            getFirstLvlList() // обновим список
        }
    }

}

data class ViewStateFirstLvlNote(
    val firstLvlList: List<FirstLvlNote>? = null,
    val error: Int? = null,
    val isLoading: Boolean = false
)