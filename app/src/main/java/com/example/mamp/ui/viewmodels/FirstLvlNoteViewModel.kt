package com.example.mamp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamp.R
import com.example.mamp.domain.models.FirstLvlNote
import com.example.mamp.domain.repository.FirstLvlNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

}

data class ViewStateFirstLvlNote(
    val firstLvlList: List<FirstLvlNote>? = null,
    val error: Int? = null,
    val isLoading: Boolean = false
)