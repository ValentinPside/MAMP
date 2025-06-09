package com.example.mamp.di.components

import com.example.mamp.ui.viewmodels.SecondLvlNoteViewModel
import dagger.Subcomponent

@Subcomponent
interface SecondLvlNoteComponent {
    fun viewModel(): SecondLvlNoteViewModel
}