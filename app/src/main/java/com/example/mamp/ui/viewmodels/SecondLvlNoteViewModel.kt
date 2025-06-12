package com.example.mamp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.models.SecondLvlNote
import com.example.mamp.domain.repository.SecondLvlNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class SecondLvlNoteViewModel @Inject constructor(
    private val repository: SecondLvlNoteRepository
) : ViewModel() {

    private val state = MutableStateFlow(ViewStateSecondLvlNote())
    fun observeUi() = state.asStateFlow()

    fun loadNotesForFirstLvl(noteId: Int) {
        viewModelScope.launch {
            val parent = repository.getFirstLvlNoteById(noteId)
            val children = repository.getSecondLvlNotesByParentId(noteId)
            state.update { it.copy(parentNote = parent, childNotes = children) }
        }
    }

    fun addSecondLvlNote(parentId: Int, name: String, date: LocalDate) {
        viewModelScope.launch {
            repository.insertSecondLvlNote(SecondLvlNote(parentId, name, date))
            loadNotesForFirstLvl(parentId)
        }
    }
}

data class ViewStateSecondLvlNote(
    val parentNote: FirstLvlNote? = null, // основной документ
    val childNotes: List<SecondLvlNote> = emptyList() // вложенные документы
)