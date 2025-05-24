package com.example.mamp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.repository.FirstLvlNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirstLvlNoteViewModel @Inject constructor(
    private val repository: FirstLvlNoteRepository
) : ViewModel() {

    private val state = MutableStateFlow(ViewStateFirstLvlNote())
    fun observeUi() = state.asStateFlow()
}

data class ViewStateFirstLvlNote(
    val item: List<FirstLvlNote> = emptyList()
)