package com.example.mamp.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamp.R
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.repository.FirstLvlNoteRepository
import com.example.mamp.utils.NoteColorFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class FirstLvlNoteViewModel @Inject constructor(
    private val repository: FirstLvlNoteRepository
) : ViewModel() {

    private val state = MutableStateFlow(ViewStateFirstLvlNote())
    fun observeUi() = state.asStateFlow()

    private val allNotes = mutableListOf<FirstLvlNote>() // все заметки без фильтра
    private val currentFilter = MutableStateFlow<NoteColorFilter?>(null)
    private val currentSearch = MutableStateFlow("")

    fun getFirstLvlList() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            try {
                val list = repository.getFirstLvlList()
                allNotes.clear()
                allNotes.addAll(list)
                applyFilters()
            } catch (e: Exception) {
                state.update { it.copy(error = R.string.error_message) }
            } finally {
                state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun setFilter(filter: NoteColorFilter?) {
        currentFilter.value = filter
        applyFilters()
        state.update { it.copy(selectedColor = filter) }
    }

    fun setSearchQuery(query: String) {
        currentSearch.value = query
        applyFilters()
    }

    private fun applyFilters() {
        val filter = currentFilter.value
        val query = currentSearch.value.lowercase()
        val showOnlyFinished = state.value.showOnlyFinished

        val filteredList = allNotes
            .filter { note ->
                val matchesColor = when (filter) {
                    NoteColorFilter.RED -> ChronoUnit.DAYS.between(LocalDate.now(), note.targetDate) <= 14
                    NoteColorFilter.YELLOW -> ChronoUnit.DAYS.between(LocalDate.now(), note.targetDate) in 15..28
                    NoteColorFilter.GREEN -> ChronoUnit.DAYS.between(LocalDate.now(), note.targetDate) > 28
                    null -> true
                }

                val matchesQuery = note.name.lowercase().contains(query)
                val matchesFinished = if (showOnlyFinished) note.isFinished == 0 else true

                matchesColor && matchesQuery && matchesFinished
            }

        val (unfinished, finished) = filteredList.partition { it.isFinished == 1 }

        val sorted = unfinished.sortedBy { it.targetDate } + finished.sortedBy { it.targetDate }

        state.update { it.copy(firstLvlList = sorted) }
    }

    fun addPdfFile(uri: Uri, address: String, targetDate: LocalDate) {
        viewModelScope.launch {
            val newNote = FirstLvlNote(
                id = 0,
                name = address,
                fileAddress = uri.toString(),
                targetDate = targetDate,
                isFinished = 1
            )
            repository.insertNote(newNote)
            getFirstLvlList()
        }
    }

    fun toggleShowOnlyFinished() {
        state.update {
            it.copy(showOnlyFinished = !it.showOnlyFinished)
        }
        applyFilters()
    }

}

data class ViewStateFirstLvlNote(
    val firstLvlList: List<FirstLvlNote>? = null,
    val error: Int? = null,
    val isLoading: Boolean = false,
    val selectedColor: NoteColorFilter? = null,
    val showOnlyFinished: Boolean = false
)