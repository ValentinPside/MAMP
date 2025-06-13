package com.example.mamp.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.models.SecondLvlNote
import com.example.mamp.domain.repository.SecondLvlNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    fun addSecondLvlNote(parentId: Int, name: String, uri: Uri) {
        viewModelScope.launch {
            val newNote = SecondLvlNote(
                id = 0,
                parentId = parentId,
                name = name,
                fileAddress = uri.toString()
            )
            repository.insertSecondLvlNote(newNote)
            loadNotesForFirstLvl(parentId)
        }
    }

    fun toggleFinishedState() {
        viewModelScope.launch {
            val currentNote = state.value.parentNote ?: return@launch
            val newStatus = if (currentNote.isFinished == 1) 0 else 1
            val updatedNote = currentNote.copy(isFinished = newStatus)
            repository.updateFirstLvlNote(updatedNote)
            loadNotesForFirstLvl(currentNote.id)
        }
    }
}

data class ViewStateSecondLvlNote(
    val parentNote: FirstLvlNote? = null,
    val childNotes: List<SecondLvlNote> = emptyList()
)