package com.example.mamp.di.components

import com.example.mamp.ui.viewmodels.FirstLvlNoteViewModel
import dagger.Subcomponent

@Subcomponent
interface FirstLvlNoteComponent {
    fun viewModel(): FirstLvlNoteViewModel
}